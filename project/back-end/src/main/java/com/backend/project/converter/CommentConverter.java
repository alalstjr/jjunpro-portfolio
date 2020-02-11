package com.backend.project.converter;

import com.backend.project.domain.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter implements Converter<String, Comment> {
    @Override
    public Comment convert(String source) {
        return new Comment(Long.parseLong(source));
    }
}
