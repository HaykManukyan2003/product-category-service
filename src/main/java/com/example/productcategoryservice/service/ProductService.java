package com.example.productcategoryservice.service;

import com.example.productcategoryservice.dto.CreateProductDto;
import com.example.productcategoryservice.dto.ProductResponseDto;
import com.example.productcategoryservice.mapper.ProductMapper;
import com.example.productcategoryservice.model.Product;
import com.example.productcategoryservice.model.User;
import com.example.productcategoryservice.repository.CategoryRepository;
import com.example.productcategoryservice.repository.ProductRepository;
import com.example.productcategoryservice.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) throws Exception {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            throw new Exception();
        }
        return optional.get();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void removeProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategory(int id) {
        return productRepository.getProductsByCategory(categoryRepository.findById(id).get());
    }

    public ProductResponseDto create(CreateProductDto productDto, User user) {
        Product product = productMapper.map(productDto);
        product.setCategory(categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalStateException("Category not found")));
        product.setUser(user);
        return productMapper.map(productRepository.save(product));
    }


    public List<ProductResponseDto> getAllProducts() {
        User currentUser = null;
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String)) {
            CurrentUser getCurrentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            currentUser = getCurrentUser.getUser();
        }
        log.info("endpoint /products called by {}", currentUser == null ? "anonymous user" : currentUser.getEmail());
        return productMapper.map(allProducts());
    }
}
