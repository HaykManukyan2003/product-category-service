package com.example.productcategoryservice.service;

import com.example.productcategoryservice.model.Category;
import com.example.productcategoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) throws Exception {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception();
        }
        return optional.get();
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void removeCategoryById(int id) {
        categoryRepository.deleteById(id);
    }
}
