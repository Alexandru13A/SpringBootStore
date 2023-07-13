package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.dto.ProductDTO;
import com.alexandru.SpringBootStore.model.Product;
import com.alexandru.SpringBootStore.service.ProductService;
import com.alexandru.SpringBootStore.service.UserService;
import com.alexandru.SpringBootStore.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private ImageUtil imageUtil;
    private final UserService userService;
    private final ProductService productService;


    public ProductController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/products")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/home/products";

    }

    @GetMapping("/admin/product/image/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        byte[] imageBytes = product.getProductImage();

        HttpHeaders headers = new HttpHeaders();
        if (headers.equals(MediaType.IMAGE_JPEG)) {
            headers.setContentType(MediaType.IMAGE_JPEG); // Sau MediaType.IMAGE_PNG, în funcție de tipul imaginii
        } else {
            headers.setContentType(MediaType.IMAGE_PNG);
        }
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/createProduct")
    public String createProduct(Model model) {
        ProductDTO productDTO = new ProductDTO();
        model.addAttribute("product", productDTO);
        return "admin/functions/create_product";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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

        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "admin/home/products";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/admin/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/shopping/admin/products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/updateProduct/{id}")
    public String updateProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        product.setProductId(id);

        model.addAttribute("product", product);
        model.addAttribute("productId", product.getProductId());
        model.addAttribute("name", product.getName());
        model.addAttribute("shortDescription", product.getShortDescription());
        model.addAttribute("longDescription", product.getLongDescription());
        model.addAttribute("price", product.getPrice());
        model.addAttribute("category", product.getCategory());


        return "admin/functions/modify_product";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/saveProduct/{id}")
    public String saveUpdatedProduct(@PathVariable Long id, @RequestParam("image") MultipartFile file, @ModelAttribute ProductDTO productDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/functions/modify_product";
        }
        Product product = productService.getProductById(id);
        product.setProductId(id);
        product.setName(productDTO.getName());
        product.setShortDescription(productDTO.getShortDescription());
        product.setLongDescription(productDTO.getLongDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());

        try {
            // Verifică dacă un fișier de imagine a fost încărcat
            product.setProductImage(file.getBytes());
        } catch (IOException e) {
            model.addAttribute("error", "Failed to read the image file.");
            return "admin/functions/modify_product";
        }

        productService.updateProduct(product);
        model.addAttribute("product", product);

        return "redirect:/shopping/admin/products";

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/view/products")
    public String adminViewProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/home/admin_view_products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/view/product/{productId}")
    public String adminViewProduct(@PathVariable("productId") Long id, Model model) {
        Product product = productService.getProductById(id);
        product.setProductId(id);
        model.addAttribute("product", product);
        return "admin/home/admin_view_product";
    }

    @GetMapping("/user/view/products")
    public String userViewProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "users/home/user_view_products";
    }

    @GetMapping("/user/view/product/{productId}")
    public String userViewProduct(@PathVariable("productId") Long id, Model model) {
        Product product = productService.getProductById(id);
        product.setProductId(id);
        model.addAttribute("product", product);
        return "users/home/user_view_product";
    }


}
