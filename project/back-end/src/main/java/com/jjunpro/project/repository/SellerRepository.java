package com.jjunpro.project.repository;

import com.jjunpro.project.domain.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<FoodMenu, Long> {

}
