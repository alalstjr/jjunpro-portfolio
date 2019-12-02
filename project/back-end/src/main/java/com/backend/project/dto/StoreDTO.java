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
    private String stoName;

    @Column(nullable = false)
    private String stoAddress;

    @Column(nullable = false)
    private String stoUrl;

    private Set<University> stoUniList = new HashSet<>();

    public Store toEntity() {
        return Store.builder()
                .stoId(stoId)
                .stoName(stoName)
                .stoAddress(stoAddress)
                .stoUrl(stoUrl)
                .stoUniList(stoUniList)
                .build();
    }
}
