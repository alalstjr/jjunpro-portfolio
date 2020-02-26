package com.jjunpro.project.service;

import com.jjunpro.project.domain.FoodMenu;
import com.jjunpro.project.domain.Store;
import com.jjunpro.project.dto.FoodMenuDTO;
import com.jjunpro.project.repository.SellerRepository;
import com.jjunpro.project.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final StoreRepository  storeRepository;

    @Override
    public void insertFoodMenu(FoodMenuDTO foodMenuDTO) {
        System.out.println("====1");
        /* 음식 메뉴를 추가합니다. */
        FoodMenu save = sellerRepository.save(foodMenuDTO.toEntity());

        System.out.println("====2");
        /* 추가한 메뉴의 정보를 Store 정보에 추가합니다. */
        Optional<Store> storeData = storeRepository.findByStoId(foodMenuDTO.getStoId());

        if (storeData.isPresent()) {
            storeData
                    .get()
                    .getFoodMenu()
                    .add(save);
            storeRepository.save(storeData.get());
        }
    }
}
