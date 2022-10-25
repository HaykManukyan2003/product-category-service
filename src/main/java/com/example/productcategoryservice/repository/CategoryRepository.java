package com.example.productcategoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Category extends JpaRepository<com.example.productcategoryservice.model.Category, Integer> {
}
