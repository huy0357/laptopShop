package com.example.demo.controller.admin;


import com.example.demo.Service.ProductService;
import com.example.demo.Service.UploadService;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Controller
public class ProductController {
    private UploadService uploadService;
    private ProductService productService;


    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }




    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        List<Product> prs = this.productService.getAllProducts();
        model.addAttribute("products", prs);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductsPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String handleCreateProduct(  @ModelAttribute("newProduct") @Valid Product pr,
                                   BindingResult newProductBindingResult,
                                   //upload file ảnh
                                   @RequestParam("hoidanitFile") MultipartFile file
    ) {
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        //Validate
        String imagePath = "";
        if (!file.isEmpty()) {
            imagePath = this.uploadService.handleSaveUploadFile(file, "product");
            System.out.println("Image path: " + imagePath); // In để kiểm tra
        }
        pr.setImage(imagePath);

        this.productService.createProduct(pr);

        return "redirect:/admin/product";
    }



    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductsPage(Model model, @PathVariable long id) {
        Optional<Product> currentProduct = this.productService.getProductById(id);
        model.addAttribute("newProduct", currentProduct.get());
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(
            @ModelAttribute("newProduct") @Valid Product pr,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        //validate
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }

        Product currentProduct = this.productService.getProductById(pr.getId()).get();
        if(currentProduct != null){

            if(!file.isEmpty()){
                String img = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(img);
            }

            currentProduct.setName(pr.getName());
            currentProduct.setPrice(pr.getPrice());
            currentProduct.setQuantity(pr.getQuantity());
            currentProduct.setDetailDesc(pr.getDetailDesc());
            currentProduct.setShortDesc(pr.getShortDesc());
            currentProduct.setFactory(pr.getFactory());
            currentProduct.setTarget(pr.getTarget());

            this.productService.createProduct(currentProduct);
        }

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";

    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product pr) {
        this.productService.deleteById(pr.getId());
        return "redirect:/admin/product";
    }


    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product pr = this.productService.getProductById(id).get();
        model.addAttribute("product", pr);
        model.addAttribute("id", id);
        return "admin/product/detail";
    }


}
