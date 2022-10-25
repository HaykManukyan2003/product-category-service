package com.example.productcategoryservice.dto;

import com.example.productcategoryservice.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductDto {
    private String title;
    private double price;
    private int count;
    private Category category;
}
