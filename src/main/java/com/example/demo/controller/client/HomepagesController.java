package com.example.demo.controller.client;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.UserService;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.domain.dto.RegisterDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomepagesController {


    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public HomepagesController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);

        return "client/homepages/show";
    }


    @RequestMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser",new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
                                 BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>> " + error.getField() + " : " + error.getDefaultMessage());
        }
        User user = this.userService.registerDTOtoUser(registerDTO);

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));

        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }
    @RequestMapping("/login")
    public String getLoginPage(Model model) {

        return "client/auth/login";
    }

    @RequestMapping("/access-deny")
    public String getDenyPage(Model model) {

        return "client/auth/deny";
    }



}
