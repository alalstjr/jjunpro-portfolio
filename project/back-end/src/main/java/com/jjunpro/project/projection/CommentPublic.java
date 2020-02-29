package com.jjunpro.project.projection;

import com.jjunpro.project.domain.File;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentPublic {

    private Long          id;
    private String        content;
    private LocalDateTime modifiedDate;
    private Long          account_id;
    private String        account_nickname;
    private File          account_photo;
}