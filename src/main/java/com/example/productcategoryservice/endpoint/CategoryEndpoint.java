package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.CreateCategoryDto;
import com.example.productcategoryservice.mapper.CategoryMapper;
import com.example.productcategoryservice.model.Category;
import com.example.productcategoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryEndpoint {

    private final CategoryMapper categoryMapper;

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> allCategories() {
        return ResponseEntity.ok(categoryMapper.map(categoryService.allCategories()));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int id) throws Exception {
        return ResponseEntity.ok(categoryMapper.map(categoryService.getCategoryById(id)));
    }

    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        categoryService.save(categoryMapper.map(createCategoryDto));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody CreateCategoryDto createCategoryDto, @PathVariable("id") int id) throws Exception {
        Category category = categoryService.getCategoryById(id);
        category.setId(createCategoryDto.getId());
        categoryService.save(category);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") int id) {
        categoryService.removeCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
