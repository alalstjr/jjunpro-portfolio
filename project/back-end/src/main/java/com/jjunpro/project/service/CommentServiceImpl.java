package com.jjunpro.project.service;

import com.jjunpro.project.domain.Comment;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.dto.AlarmDTO;
import com.jjunpro.project.dto.CreateCommentDTO;
import com.jjunpro.project.projection.CommentPublic;
import com.jjunpro.project.repository.AlarmRepository;
import com.jjunpro.project.repository.CommentRepository;
import com.jjunpro.project.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository    commentRepository;
    private final UniversityRepository universityRepository;
    private final AlarmRepository      alarmRepository;

    @Override
    public CommentPublic save(CreateCommentDTO dto) {
        Comment              commentData    = null;
        University           uniData        = null;
        Optional<University> universityData = universityRepository.findById(dto.getUniId());

        /* DB에 해당 댓글이 들어가는 DATA가 존재하는지 확인합니다. */
        if (universityData.isPresent()) {
            /* Comment <=> University 양방향 관계 */
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
                            .getId()) )) {
                AlarmDTO alearmDTO = new AlarmDTO();
                alearmDTO.setUserId(uniData
                        .getAccount()
                        .getId());
                alearmDTO.setDataId(uniData.getId());
                alearmDTO.setDataContent(dto.getContent());
                alearmDTO.setWriteId(dto
                        .getAccount()
                        .getUsername());
                alearmDTO.setDataType("Comment");
                alarmRepository.save(alearmDTO.toEntity());
            }
        }

        return commentRepository.findByPublicId(commentData.getId());
    }
}
