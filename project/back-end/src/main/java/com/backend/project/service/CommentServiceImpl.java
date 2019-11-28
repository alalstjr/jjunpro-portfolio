package com.backend.project.service;

import com.backend.project.domain.Comment;
import com.backend.project.domain.University;
import com.backend.project.dto.CommentSaveDTO;
import com.backend.project.repository.CommentRepository;
import com.backend.project.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository comment;

    @Autowired
    private UniversityRepository universityRepository;

    @Override
    public Comment save(CommentSaveDTO dto) {
        Comment commentData = comment.save(dto.toEntity());

        System.out.println(commentData.getContent());

        if(commentData.getId() != null) {
            University universityData = universityRepository.findById(dto.getUniId()).get();
            universityData.getComments().add(commentData);

            universityRepository.save(universityData);
        }

        return commentData;
    }
}
