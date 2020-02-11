package com.backend.project.repository;

import com.backend.project.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryDSL {

    Optional<Store> findByStoId(String stoId);
}
