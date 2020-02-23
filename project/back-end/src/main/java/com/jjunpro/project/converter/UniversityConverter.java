package com.jjunpro.project.converter;

import com.jjunpro.project.dto.UniversityValidDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UniversityConverter implements Converter<String, UniversityValidDTO> {
    @Override
    public UniversityValidDTO convert(String source) {
        return new UniversityValidDTO(Long.parseLong(source));
    }
}

