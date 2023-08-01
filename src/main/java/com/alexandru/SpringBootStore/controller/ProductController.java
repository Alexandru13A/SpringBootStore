package com.alexandru.SpringBootStore.controller;

import com.alexandru.SpringBootStore.dto.ProductDTO;
import com.alexandru.SpringBootStore.model.Product;
import com.alexandru.SpringBootStore.model.User;
import com.alexandru.SpringBootStore.service.CartItemService;
import com.alexandru.SpringBootStore.service.ProductService;
import com.alexandru.SpringBootStore.service.UserService;
import com.alexandru.SpringBootStore.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shopping")
public class ProductController {
    @Autowired
    private final ImageUtil imageUtil;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;


    public ProductController(ImageUtil imageUtil, CartItemService cartItemService, ProductService productService, UserService userService) {
        this.imageUtil = imageUtil;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/products")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/home/products";

    }

    @GetMapping("/product/image/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        return imageUtil.getProductImage(id);
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
        product.setBrand(productDTO.getBrand());
        product.setType(productDTO.getType());
        product.setGender(productDTO.getGender());

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
        model.addAttribute("brand", product.getBrand());
        model.addAttribute("type", product.getType());
        model.addAttribute("gender", product.getGender());
        model.addAttribute("productImage", product.getProductImage());

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
        product.setBrand(productDTO.getBrand());
        product.setType(productDTO.getType());
        product.setGender(productDTO.getGender());

        try {
            product.setProductImage(file.getBytes());
        } catch (IOException e) {
            model.addAttribute("error", "Failed to read the image file.");
            return "admin/functions/modify_product";
        }

        productService.updateProduct(product);
        model.addAttribute("product", product);

        return "redirect:/shopping/admin/products";

    }


    @GetMapping("/view/product/{productId}")
    public String viewProduct(@PathVariable("productId") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        List<String> shoeSizes = cartItemService.shoesSize();
        List<String> clothesSizes = cartItemService.clothesSize();

        if (user.getRole().equals("ADMIN")) {
            Product product = productService.getProductById(id);
            product.setProductId(id);
            model.addAttribute("product", product);
            model.addAttribute("shoeSizes", shoeSizes);
            model.addAttribute("clothesSizes", clothesSizes);
            return "admin/home/admin_view_product";

        } else {
            Product product = productService.getProductById(id);
            product.setProductId(id);
            model.addAttribute("product", product);
            model.addAttribute("shoeSizes", shoeSizes);
            model.addAttribute("clothesSizes", clothesSizes);
            return "users/home/user_view_product";
        }
    }


    @RequestMapping(value = "/view/products", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewProducts(@RequestParam(value = "searchTerm", required = false) String searchTerm,
                               @RequestParam(value = "sort", required = false) String sort,
                               @RequestParam(value = "category", required = false) String category,
                               Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());

        List<Product> products;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            products = productService.searchProductByName(searchTerm.toLowerCase());
        } else if (category != null && !category.isEmpty() && sort != null && !sort.isEmpty()) {
            products = productService.sortByCategoryAndColumn(category, sort);
        } else if (category != null && !category.isEmpty()) {
            products = productService.sortByCategoryAndColumn(category, null);
        } else if (sort != null && !sort.isEmpty()) {
            products = productService.sortByCategoryAndColumn(null, sort);
        } else {
            products = productService.getAllProducts();
        }


        if (user.getRole().equals("ADMIN")) {
            model.addAttribute("products", products);
            return "admin/home/admin_view_products";
        } else {
            model.addAttribute("products", products);
            return "users/home/user_view_products";
        }
    }

    @RequestMapping(value = "/view/products/sorted", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewSortedProducts() {


        return "null";
    }

}
