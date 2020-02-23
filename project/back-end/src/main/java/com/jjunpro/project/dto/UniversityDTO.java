package com.jjunpro.project.dto;

import com.jjunpro.project.annotation.MaxFile;
import com.jjunpro.project.annotation.UserDataMatch;
import com.jjunpro.project.annotation.UserExistence;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Comment;
import com.jjunpro.project.domain.File;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.util.AccountUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@UserDataMatch(id = "id", domain = "university")
public class UniversityDTO {

    private Long id;

    @Size(max = 50, message = "제목이 너무 깁니다.")
    @NotBlank(message = "제목을 작성해 주세요.")
    private String uniSubject;

    private String uniAtmosphere = null;

    private String uniPrice = null;

    @NotBlank(message = "내용을 작성해 주세요.")
    @Type(type = "text")
    private String uniContent;

    @Size(max = 15, message = "대학교 이름이 너무 깁니다.")
    @NotBlank(message = "대학교 입력은 필수입니다.")
    private String uniName;

    private String uniTag;

    //@Size(max = 5, message = "점수는 5점 만점입니다.")
    private Integer uniStar;

    private Set<Account> uniLike = new HashSet<>();

    private String ip;

    /* ACCOUNT DATA */
    private Account account;

    /* STORE DATA */
    @Column(nullable = false)
    private String stoId;

    @Column(nullable = false)
    private String stoName;

    @Column(nullable = false)
    private String stoAddress;

    @Column(nullable = false)
    private String stoUrl;

    /* 서버에 저장된 File 의 정보 files -> fileData 순으로 엔티티에 변환되어 저장 */
    private List<File> fileData;

    /* UPDATE 기존 파일의 삭제 정보를 저장하는 변수 입니다. */
    private Long[] removeFiles;

    /* 클라이언트에서 받은 Files */
    @MaxFile
    private MultipartFile[] files;

    /* COMMENT DATA */
    private Set<Comment> comments = new HashSet<>();

    public University toEntity() {
        return University
                .builder()
                .id(id)
                .uniSubject(uniSubject)
                .uniAtmosphere(uniAtmosphere)
                .uniPrice(uniPrice)
                .uniContent(uniContent)
                .uniName(uniName)
                .uniTag(uniTag)
                .uniStar(uniStar)
                .uniLike(uniLike)
                .ip(ip)
                .account(account)
                .files(fileData)
                .comments(comments)
                .build();
    }

    /* User Ip and User Account 기본값을 설정합니다. */
    public void defaultSetting(
            String userIp,
            AccountUtil accountUtil,
            Authentication authentication
    ) {
        /* Account Info */
        Optional<Account> accountData = accountUtil.accountInfo(authentication);

        accountData.ifPresent(value -> this.account = value);
        this.ip = userIp;
    }
}
