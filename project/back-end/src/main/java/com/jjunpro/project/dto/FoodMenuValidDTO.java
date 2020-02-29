package com.jjunpro.project.dto;

import com.jjunpro.project.annotation.DataMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.jjunpro.project.enums.ColumnType.FOODMENU;
import static com.jjunpro.project.enums.ColumnType.STOID;

@Getter
@Setter
@NoArgsConstructor
public class FoodMenuValidDTO {

    /* 접근하는 StoreId */
    @DataMatch(column = FOODMENU, message = "접근하려는 정보와 맞지 않습니다.")
    private String id;
}
