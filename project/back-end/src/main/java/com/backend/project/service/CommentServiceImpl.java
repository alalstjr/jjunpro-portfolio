package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.Comment;
import com.backend.project.domain.University;
import com.backend.project.dto.AlarmDTO;
import com.backend.project.dto.CommentSaveDTO;
import com.backend.project.projection.CommentPublic;
import com.backend.project.repository.AlarmRepository;
import com.backend.project.repository.CommentRepository;
import com.backend.project.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository    comment;
    private final UniversityRepository university;
    private final AlarmRepository      alarm;

    @Override
    public CommentPublic save(CommentSaveDTO dto) {
        Comment              commentData    = null;
        University           uniData        = null;
        Optional<University> universityData = university.findById(dto.getUniId());

        // DB에 해당 댓글이 들어가는 DATA가 존재하는지 확인합니다.
        if (universityData.isPresent()) {
            dto.setUniversity(universityData.get());    // Comment <=> University 양방향 관계
            commentData = comment.save(dto.toEntity());

            // 정상적으로 DATA가 전송이 되었는지 확인합니다.
            universityData
                    .get()
                    .getComments()
                    .add(commentData);
            uniData = university.save(universityData.get());

            // 댓글이 정상적으로 작성이 됐고 댓글 작성자와 게시글 작성자가 다를경우
            // 알람 테이블에 추가
            if (uniData.getId() != null && ( !dto
                    .getAccount()
                    .getId()
                    .equals(uniData
                            .getAccount()
                            .getId()) )) {
                AlarmDTO alearmDTO = new AlarmDTO();
                alearmDTO.setUserId(uniData
                        .getAccount()
                        .getId());
                alearmDTO.setDataId(uniData.getId());
                alearmDTO.setDataContent(dto.getContent());
                alearmDTO.setWriteId(dto
                        .getAccount()
                        .getUserId());
                alearmDTO.setDataType("Comment");
                alarm.save(alearmDTO.toEntity());
            }
        }

        return comment.findByPublicId(commentData.getId());
    }

    @Override
    public void deleteData(
            Long id,
            Account account
    ) {
        comment.deleteData(
                id,
                account
        );
    }

    @Override
    public void deleteUniComment(
            Long id,
            Account account
    ) {
        comment.deleteUniComment(
                id,
                account
        );
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return comment.findById(id);
    }

    @Override
    public List<CommentPublic> findByCommentList(
            Long uniId,
            Account account
    ) {
        List<CommentPublic> result = null;

        // 해당 POST에 댓글이 존재하는지 확인합니다. 불필요한 DB검색 방지
        Optional<University> universityData = university.findById(uniId);

        if (universityData.isPresent()) {
            if (!universityData
                    .get()
                    .getComments()
                    .isEmpty()) {
                result = comment.findByCommentList(uniId);
            }
        }

        return result;
    }
}
