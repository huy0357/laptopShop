package com.example.demo.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboarController {


    @GetMapping("/admin")
    public String getDashboar() {
        return "admin/dashboard/show";
    }

}
