package com.jjunpro.project.repository;

import com.jjunpro.project.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryDSL {

    Optional<Store> findByStoId(String stoId);
}
