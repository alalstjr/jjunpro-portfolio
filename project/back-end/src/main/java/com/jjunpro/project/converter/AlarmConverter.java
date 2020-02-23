package com.jjunpro.project.converter;

import com.jjunpro.project.dto.AlarmDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AlarmConverter implements Converter<String, AlarmDTO> {
    @Override
    public AlarmDTO convert(String source) {
        return new AlarmDTO(Long.parseLong(source));
    }
}