package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.CreateProductDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.mapper.ProductMapper;
import com.example.productcategoryservice.model.Product;
import com.example.productcategoryservice.security.CurrentUser;
import com.example.productcategoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductEndpoint {

    private final ProductMapper productMapper;

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> allProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> productById(@PathVariable("id") int id) throws Exception {
        return ResponseEntity.ok(productMapper.map(productService.getProductById(id)));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody CreateProductDto productDto,
                                                            @AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(productDto, currentUser.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody CreateProductDto createProductDto,
                                           @PathVariable("id") int id,
                                           @AuthenticationPrincipal CurrentUser currentUser) throws Exception {
        Product product = productService.getProductById(id);
        if (currentUser.getUser().getId() == product.getUser().getId()) {
            product.setTitle(productMapper.map(createProductDto).getTitle());
            product.setPrice(productMapper.map(createProductDto).getPrice());
            product.setCategory(productMapper.map(createProductDto).getCategory());
            productService.save(product);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") int id, @AuthenticationPrincipal CurrentUser currentUser) throws Exception {
        if (currentUser.getUser().getId() == productService.getProductById(id).getUser().getId()) {
            productService.removeProductById(id);
            return ResponseEntity.noContent().build();
        } else
            log.error("failed to delete product", new Throwable("access denied: user id does not match user_id in product"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/byCategory/{id}")
    public ResponseEntity<?> getProductsByCategoryId(@PathVariable("id") int id) {
        return ResponseEntity.ok(productService.getProductsByCategory(id));
    }
}


