package com.fashion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    // Root path directly maps to index.html
    @GetMapping("/")
    public String indexPage() {
        return "index"; 
    }
}