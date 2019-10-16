package com.backend.project.dto;

import com.backend.project.domain.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class StoreDTO {

    @Column(nullable = false)
    private String stoId;

    @Column(nullable = false)
    private String stoAddress;

    public Store toEntity() {
        return Store.builder()
                .stoId(stoId)
                .stoAddress(stoAddress)
                .build();
    }
}
