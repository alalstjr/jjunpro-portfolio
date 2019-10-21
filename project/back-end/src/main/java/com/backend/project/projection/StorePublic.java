package com.backend.project.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StorePublic {
    private Long id;
    private List<UniversityPublic> stoUniList;
}
