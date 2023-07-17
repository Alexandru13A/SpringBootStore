package com.alexandru.SpringBootStore.utils;

import com.alexandru.SpringBootStore.model.Product;
import com.alexandru.SpringBootStore.service.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ImageUtil {

    private final ProductService productService;

    public ImageUtil(ProductService productService) {
        this.productService = productService;
    }

    public ResponseEntity<byte[]> getProductImage(Long id) {
        Product product = productService.getProductById(id);
        byte[] imageBytes = product.getProductImage();

        HttpHeaders headers = new HttpHeaders();
        if (headers.equals(MediaType.IMAGE_JPEG)) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        } else {
            headers.setContentType(MediaType.IMAGE_PNG);
        }
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

}
