package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.CreateProductDto;
import com.example.productcategoryservice.mapper.ProductMapper;
import com.example.productcategoryservice.model.Product;
import com.example.productcategoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductEndpoint {

    private final ProductMapper productMapper;

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> allProducts() {
        return ResponseEntity.ok(productMapper.map(productService.allProducts()));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int id) throws Exception {
        return ResponseEntity.ok(productMapper.map(productService.getProductById(id)));
    }

    @PostMapping("/products")
    public ResponseEntity<?> createCategory(@RequestBody CreateProductDto createProductDto) {
        productService.save(productMapper.map(createProductDto));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody CreateProductDto createProductDto, @PathVariable("id") int id) throws Exception {
        Product product = productService.getProductById(id);
        product.setTitle(productMapper.map(createProductDto).getTitle());
        product.setPrice(productMapper.map(createProductDto).getPrice());
        product.setCategory(productMapper.map(createProductDto).getCategory());
        productService.save(product);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") int id) {
        productService.removeProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byCategory/{id}")
    public ResponseEntity<?> getProductsByCategoryId(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.getProductsByCategory(id));
    }
}
