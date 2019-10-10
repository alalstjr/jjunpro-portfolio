package com.backend.project.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UniversityPublic {

    private Long id;
    private String uniSubject;
    private String uniContent;
    private String uniName;
    private String[] uniTag;
    private String uniLocal;
    private Integer uniStar;
    private String uniIp;
    private LocalDateTime modifiedDate;
    private Long account_id;
    private String account_nickname;
    private Integer uniLike;
}
