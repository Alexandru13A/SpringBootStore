package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.dto.ProductDTO;
import com.alexandru.SpringBootStore.model.Product;
import com.alexandru.SpringBootStore.service.ProductService;
import com.alexandru.SpringBootStore.service.UserService;
import com.alexandru.SpringBootStore.utils.ImageUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/shopping")
public class ProductController {
    private final ImageUtil imageUtil;
    private final UserService userService;
    private final ProductService productService;


    public ProductController(ImageUtil imageUtil, UserService userService, ProductService productService) {
        this.imageUtil = imageUtil;
        this.userService = userService;
        this.productService = productService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/products")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();

        for (Product product : products) {
            byte[] productImage = product.getProductImage();
            if (productImage != null) {
                String base64Image = imageUtil.getBase64Image(productImage);
                product.setProductImage(base64Image.getBytes());
            }
        }


        model.addAttribute("products", products);
        return "admin/home/products";

    }

    @GetMapping("/admin/createProduct")
    public String createProduct(Model model) {
        ProductDTO productDTO = new ProductDTO();
        model.addAttribute("product", productDTO);
        return "admin/functions/create_product";
    }

    @PostMapping("/admin/product/save")
    public String saveProduct(@Valid @ModelAttribute ProductDTO productDTO, @RequestParam("file") MultipartFile file, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/functions/create_product";
        }
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setShortDescription(productDTO.getShortDescription());
        product.setLongDescription(productDTO.getLongDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());

        try {
            product.setProductImage(file.getBytes());
        } catch (IOException e) {
            model.addAttribute("error", "Failed to read the image file.");
            return "admin/functions/create_product";
        }
        productService.saveProduct(product);
        model.addAttribute("product", product);

        return "admin/home/products";
    }



}
