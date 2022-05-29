package com.hw.demoweb.service;

import com.hw.demoweb.model.Product;
import com.hw.demoweb.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.getAllProducts();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product getById(Long id) {
        return productRepository.getProductById(id).orElse(Product.builder().build());
    }

    public void update(Long id, Product product) {
        productRepository.update(id, product);
    }

    public void delete(Long id) {
        productRepository.delete(id);
    }

}
