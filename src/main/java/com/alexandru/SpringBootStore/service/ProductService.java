package com.alexandru.SpringBootStore.service;

import com.alexandru.SpringBootStore.model.Product;
import com.alexandru.SpringBootStore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProductByName(String searchTerm) {
        return productRepository.findByName(searchTerm);
    }

    public List<Product> sortByCategoryAndColumn(String category,String sort) {
        return productRepository.sortByCategoryAndColumn(category,sort);
    }


}
