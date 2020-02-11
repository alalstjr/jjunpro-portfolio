package com.backend.project.converter;

import com.backend.project.dto.AlarmDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AlarmConverter implements Converter<String, AlarmDTO> {
    @Override
    public AlarmDTO convert(String source) {
        return new AlarmDTO(Long.parseLong(source));
    }
}
