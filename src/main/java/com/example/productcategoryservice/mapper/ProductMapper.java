package com.example.productcategoryservice.mapper;

import com.example.productcategoryservice.dto.CategoryResponseDto;
import com.example.productcategoryservice.dto.CreateCategoryDto;
import com.example.productcategoryservice.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    List<CategoryResponseDto> map(List<Category> categoryList);

    CategoryResponseDto map(Category category);

    Category map(CreateCategoryDto createCategoryDto);
}
