package com.jjunpro.project.converter;

import com.jjunpro.project.dto.CommentDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter implements Converter<String, CommentDTO> {
    @Override
    public CommentDTO convert(String source) {
        return new CommentDTO(Long.parseLong(source));
    }
}