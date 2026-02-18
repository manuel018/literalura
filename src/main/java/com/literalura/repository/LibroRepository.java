package com.literalura.repository;

import com.literalura.entity.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<LibroEntity, Integer> {

    List<LibroEntity> findByTitleContainingIgnoreCase(String titulo);
}
