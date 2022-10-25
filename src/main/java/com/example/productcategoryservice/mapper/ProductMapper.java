package com.example.productcategoryservice.mapper;

import com.example.productcategoryservice.dto.CreateProductDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    List<ProductResponseDto> map(List<Product> productList);

    ProductResponseDto map(Product product);

    Product map(CreateProductDto createProductDto);
}
