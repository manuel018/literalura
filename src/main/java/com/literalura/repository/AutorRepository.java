package com.literalura.repository;

import com.literalura.entity.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<AutorEntity, Integer> {
    Optional<AutorEntity> findByNameIgnoreCase(String nome);
}
