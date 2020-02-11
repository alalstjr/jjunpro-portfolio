package com.backend.project.projection;

import com.backend.project.domain.File;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class UniversityPublic implements Comparable<UniversityPublic> {
    private Long          id;
    private String        uniSubject;
    private String        uniAtmosphere;
    private String        uniPrice;
    private String        uniContent;
    private String        uniName;
    private String        uniTag;
    private Integer       uniStar;
    private LocalDateTime modifiedDate;
    private Long          account_id;
    private String        account_nickname;
    private String[]      account_urlList;
    private Integer       uniLike;
    private Boolean       uniLikeState;
    private List<File>    files;
    private File          photo;
    private StorePublic   storePublic;
    private Integer       uniComment;

    @Override
    public int compareTo(UniversityPublic o) {
        return Integer.compare(
                this.uniLike,
                o.getUniLike()
        );
    }
}
