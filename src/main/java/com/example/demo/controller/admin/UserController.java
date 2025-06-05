package com.example.demo.controller.admin;


import com.example.demo.Service.UploadService;
import com.example.demo.Service.UserService;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
public class UserController {

     private final UserService userService;
     private final UploadService uploadService;
     private final PasswordEncoder passwordEncoder;


    public UserController(UploadService uploadService, UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;

        this.passwordEncoder = passwordEncoder;
    }


    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }



    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User users = this.userService.getUserById(id);
        model.addAttribute("user", users);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPages(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUserPages( Model model, @ModelAttribute("newUser") @Valid User hoidanit,
                                   BindingResult newUserBindingResult,
                                   //upload file ảnh
                                   @RequestParam("hoidanitFile") MultipartFile file
                                ) {

        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors ) {
            System.out.println (">>>>>"+ error.getField() + " - " + error.getDefaultMessage());
        }
        //Validate

        if(newUserBindingResult.hasErrors()) {
            return "admin/user/create";
        }

            String avatar = this.uploadService.handleSaveUploadFile(file,"avatar");
            String hashPassword = this.passwordEncoder.encode(hoidanit.getPassword());

            hoidanit.setAvatar(avatar);
            hoidanit.setPassword(hashPassword);
            hoidanit.setRole(this.userService.getRoleByName(hoidanit.getRole().getName()));

            this.userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";

    }
    @RequestMapping("/admin/user/update/{id}")
    public String getUserUpdate(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("newUser", user);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User hoidanit) {
        // Lấy người dùng từ cơ sở dữ liệu
        User Cuser = this.userService.getUserById(hoidanit.getId());

        if (Cuser != null) {
            // Cập nhật các trường khác của người dùng
            Cuser.setAddress(hoidanit.getAddress());
            Cuser.setPhone(hoidanit.getPhone());
            Cuser.setFullName(hoidanit.getFullName());

            // Cập nhật vai trò cho người dùng
            Cuser.setRole(this.userService.getRoleByName(hoidanit.getRole().getName()));

            // Lưu đối tượng đã cập nhật
            this.userService.handleSaveUser(Cuser);
        }

        // Sau khi cập nhật xong, chuyển hướng về trang quản lý người dùng
        return "redirect:/admin/user";
    }


    @GetMapping("/admin/user/delete/{id}")
    public String deleteUser(Model model, @PathVariable long id) {
        model.addAttribute("id",id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }


    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User eric) {
       this.userService.deleteById(eric.getId());
        return "redirect:/admin/user";
    }




}
