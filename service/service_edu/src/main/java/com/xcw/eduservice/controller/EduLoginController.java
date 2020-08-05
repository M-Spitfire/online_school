package com.xcw.eduservice.controller;

import com.xcw.utils.R;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu-service/user")
@CrossOrigin
@ComponentScan(basePackages = "com.xcw")
public class EduLoginController {
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public R getInfo() {
        return R.ok().data("roles", "[xxx]").data("name", "xcw").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
