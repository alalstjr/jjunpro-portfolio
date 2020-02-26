package com.jjunpro.project.controller;

import com.jjunpro.project.dto.FoodMenuDTO;
import com.jjunpro.project.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    /**
     * INSERT FoodMenu DATA
     */
    @PostMapping("")
    public ResponseEntity<?> insertFoodMenu(
            @Valid
            @RequestBody
                    FoodMenuDTO foodMenuDTO,
            BindingResult bindingResult
    ) throws BindException {
        /* 유효성 검사 후 최종 반환합니다. */
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        sellerService.insertFoodMenu(foodMenuDTO);

        return null;
    }
}
