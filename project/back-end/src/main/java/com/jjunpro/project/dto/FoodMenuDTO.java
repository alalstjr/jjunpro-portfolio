package com.jjunpro.project.dto;

import com.jjunpro.project.annotation.DataMatch;
import com.jjunpro.project.annotation.SubMenuData;
import com.jjunpro.project.domain.File;
import com.jjunpro.project.domain.FoodMenu;
import com.jjunpro.project.domain.SubMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

import static com.jjunpro.project.enums.ColumnType.STOID;

@Getter
@Setter
@NoArgsConstructor
public class FoodMenuDTO {

    /* 접근하는 StoreId */
    @NotBlank(message = "메뉴를 등록하려는 가게정보는 필수입니다.")
    @DataMatch(column = STOID)
    private String stoId;

    @NotBlank(message = "음식이름을 작성해 주세요.")
    private String name;

    @NotBlank(message = "내용을 작성해 주세요.")
    private String content;

    @NotBlank(message = "가격을 작성해 주세요.")
    private String price;

    /* 클라이언트에서 받은 File */
    private MultipartFile photo;

    /* 서버에 저장된 File 의 정보 files -> fileData 순으로 엔티티에 변환되어 저장 */
    private File fileData;

    @SubMenuData
    private Set<SubMenu> subMenus = new HashSet<>();

    public FoodMenu toEntity() {
        return FoodMenu
                .builder()
                .name(name)
                .content(content)
                .price(Integer.parseInt(price))
                .photo(fileData)
                .subMenus(subMenus)
                .build();
    }

    @Override
    public String toString() {
        return "FoodMenuDTO{" + "name='" + name + '\'' + ", content='" + content + '\'' + ", price=" + price + ", photo=" + photo + ", subMenus=" + subMenus + '}';
    }
}
