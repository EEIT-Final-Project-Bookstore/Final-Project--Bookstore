package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FallbackController {
    @GetMapping("/{path:[^\\.]*}") // 处理前端 SPA 的所有路径
    public String forward() {
        return "forward:/index.html";
    }
}
