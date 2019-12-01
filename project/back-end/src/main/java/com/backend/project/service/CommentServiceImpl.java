package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.Comment;
import com.backend.project.domain.University;
import com.backend.project.dto.CommentSaveDTO;
import com.backend.project.projection.CommentPublic;
import com.backend.project.repository.CommentRepository;
import com.backend.project.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository comment;

    @Autowired
    private UniversityRepository university;

    @Override
    public CommentPublic save(CommentSaveDTO dto) {

        Comment commentData = null;
        Optional<University> universityData = university.findById(dto.getUniId());

        // DB에 해당 댓글이 들어가는 DATA가 존재하는지 확인합니다.
        if(universityData.isPresent()) {
            dto.setUniversity(universityData.get());    // Comment <=> University 양방향 관계
            commentData = comment.save(dto.toEntity());

            // 정상적으로 DATA가 전송이 되었는지 확인합니다.
            universityData.get().getComments().add(commentData);
            university.save(universityData.get());
        }

        return comment.findByPublicId(commentData.getId());
    }

    @Override
    public void deleteData(Long id, Account account) {
        comment.deleteData(id, account);
    }

    @Override
    public void deleteUniComment(Long id, Account account) {
        comment.deleteUniComment(id, account);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return comment.findById(id);
    }

    @Override
    public List<CommentPublic> findByCommentList(Long id) {

        List<CommentPublic> result = null;

        // 해당 POST에 댓글이 존재하는지 확인합니다. 불필요한 DB검색 방지
        if(!university.findById(id).get().getComments().isEmpty()) {
            result = comment.findByCommentList(id);
        }

        return result;
    }
}
