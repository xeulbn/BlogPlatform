package org.example.blogplatform.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookieAPITestController {
    @GetMapping("/api/sendCookie")
    public String sendCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("auth")) {
                    return cookie.getValue();
                }
            }
        }
        return "no cookie";
    }
}
