package com.backend.project.dto;

import com.backend.project.domain.Store;
import com.backend.project.domain.University;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class StoreDTO {

    @Column(nullable = false)
    private String stoId;

    @Column(nullable = false)
    private String stoAddress;

    private Set<University> stoUniList = new HashSet<>();

    public Store toEntity() {
        return Store.builder()
                .stoId(stoId)
                .stoAddress(stoAddress)
                .stoUniList(stoUniList)
                .build();
    }
}
