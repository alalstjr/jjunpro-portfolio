package com.jjunpro.project.service;

import com.jjunpro.project.dto.FoodMenuDTO;
import com.jjunpro.project.dto.FoodMenuValidDTO;

public interface SellerService {

    Long insertFoodMenu(FoodMenuDTO foodMenuDTO);

    Boolean deleteFoodMenu(Long foodMenuId);
}
