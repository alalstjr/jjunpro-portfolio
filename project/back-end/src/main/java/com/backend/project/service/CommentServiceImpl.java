package com.backend.project.service;

import com.backend.project.domain.Comment;
import com.backend.project.domain.University;
import com.backend.project.dto.CommentSaveDTO;
import com.backend.project.projection.CommentPublic;
import com.backend.project.repository.CommentRepository;
import com.backend.project.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository comment;

    @Autowired
    private UniversityRepository universityRepository;

    @Override
    public CommentPublic save(CommentSaveDTO dto) {

        Comment commentData = null;
        Optional<University> universityData = universityRepository.findById(dto.getUniId());

        // DB에 해당 댓글이 들어가는 DATA가 존재하는지 확인합니다.
        if(universityData.isPresent()) {
            commentData = comment.save(dto.toEntity());

            // 정상적으로 DATA가 전송이 되었는지 확인합니다.
            universityData.get().getComments().add(commentData);
            universityRepository.save(universityData.get());
        }

        return comment.findByPublicId(commentData.getId());
    }
}
