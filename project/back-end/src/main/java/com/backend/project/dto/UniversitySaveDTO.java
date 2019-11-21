package com.backend.project.dto;

import com.backend.project.domain.Account;
import com.backend.project.domain.File;
import com.backend.project.domain.University;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UniversitySaveDTO {

    @Size(max=50, message = "제목이 너무 깁니다.")
    @NotBlank(message = "제목을 작성해 주세요.")
    private String uniSubject;

    @NotBlank(message = "내용을 작성해 주세요.")
    @Type(type = "text")
    private String uniContent;

    @Size(max=10, message = "대학교 이름이 너무 깁니다.")
    @NotBlank(message = "대학교 이름을 작성해 주세요.")
    private String uniName;

    private String[] uniTag;

    private Integer uniStar;

    private Set<Account> uniLike;

    private String uniIp;

    private Account account;

    @Column(nullable = false)
    private String stoId;

    @Column(nullable = false)
    private String stoAddress;

    // 서버에 저장된 File 의 정보 files -> fileData 순으로 엔티티에 변환되어 저장
    private List<File> fileData;

    // 클라이언트에서 받은 Files
    private MultipartFile[] files;

    public University toEntity() {
        return University.builder()
                .uniSubject(uniSubject)
                .uniContent(uniContent)
                .uniName(uniName)
                .uniTag(uniTag)
                .uniStar(uniStar)
                .uniLike(uniLike)
                .uniIp(uniIp)
                .account(account)
                .files(fileData)
                .build();
    }
}
