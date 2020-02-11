package com.backend.project.converter;

import com.backend.project.dto.UniversityDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UniversityConverter implements Converter<String, UniversityDTO> {
    @Override
    public UniversityDTO convert(String source) {
        return new UniversityDTO(Long.parseLong(source));
    }
}
