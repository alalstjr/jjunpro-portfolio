package com.jjunpro.project.service;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Comment;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.dto.AlarmDTO;
import com.jjunpro.project.dto.CreateCommentDTO;
import com.jjunpro.project.enums.AlarmType;
import com.jjunpro.project.projection.CommentPublic;
import com.jjunpro.project.repository.AlarmRepository;
import com.jjunpro.project.repository.CommentRepository;
import com.jjunpro.project.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository    commentRepository;
    private final UniversityRepository universityRepository;
    private final AlarmRepository      alarmRepository;

    @Override
    public CommentPublic save(CreateCommentDTO dto) {
        University           uniData;
        Comment              commentData    = null;
        Optional<University> universityData = universityRepository.findById(dto.getUniId());

        /* DB에 해당 댓글이 들어가는 DATA가 존재하는지 확인합니다. */
        if (universityData.isPresent()) {
            /* University <= Comment 단방향 관계 */
            dto.setUniversity(universityData.get());
            commentData = commentRepository.save(dto.toEntity());

            /* 정상적으로 DATA가 전송이 되었는지 확인합니다. */
            universityData
                    .get()
                    .getComments()
                    .add(commentData);
            uniData = universityRepository.save(universityData.get());

            /*
             * 댓글이 정상적으로 작성이 됐고 댓글 작성자와 게시글 작성자가 다를경우
             * 알람 테이블에 추가
             * */
            if (uniData.getId() != null && ( !dto
                    .getAccount()
                    .getId()
                    .equals(uniData
                            .getAccount()
                            .getId())
            )) {
                AlarmDTO alearmDTO = AlarmDTO
                        .builder()
                        .userId(uniData
                                .getAccount()
                                .getId())
                        .dataId(uniData.getId())
                        .dataContent(dto.getContent())
                        .writeId(dto
                                .getAccount()
                                .getUsername())
                        .dataType(AlarmType.NOTICE)
                        .build();

                alarmRepository.save(alearmDTO.toEntity());
            }
        }

        return commentRepository.findByPublicId(commentData.getId());
    }

    @Override
    public List<CommentPublic> findByCommentList(
            Long uniId,
            Account account
    ) {
        List<CommentPublic> result = null;

        // 해당 POST에 댓글이 존재하는지 확인합니다. 불필요한 DB검색 방지
        Optional<University> universityData = universityRepository.findById(uniId);

        if (universityData.isPresent()) {
            if (!universityData
                    .get()
                    .getComments()
                    .isEmpty()) {
                result = commentRepository.findByCommentList(uniId);
            }
        }

        return result;
    }

    @Override
    public void deleteData(
            Long id,
            Account account
    ) {
        /* 단방향 관계 */
        Optional<Comment> comment = commentRepository.findById(id);
        Optional<University> university = universityRepository.findById(comment
                .get()
                .getUniversity()
                .getId());

        /* 삭제하는 댓글을 연결되어 있는 University 내부에서 삭제합니다. */
        university
                .get()
                .getComments()
                .remove(comment.get());

        /* 댓글을 삭제한 University 정보를 저장합니다. */
        universityRepository.save(university.get());

        commentRepository.deleteData(
                id,
                account
        );
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }
}
