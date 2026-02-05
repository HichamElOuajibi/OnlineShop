package com.codeonlineshop.onlineshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Value("${spring.application.name}")
    private String appName;

    /**
     * Serves the home page. Returns the view name for the static index.html.
     */
    @RequestMapping("/")
    public String index() {
        System.out.println("appName:" + appName);
        return "index.html";
    }
}
