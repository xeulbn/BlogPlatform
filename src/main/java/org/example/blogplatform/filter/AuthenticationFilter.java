package org.example.blogplatform.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.blogplatform.service.UserService;
import org.example.blogplatform.util.UserContext;

import java.io.IOException;

public class AuthenticationFilter implements Filter {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        if (token != null) {
            String userId = validateTokenAndUserId(token);
            if (userId != null) {
                UserContext.setUser(userId);
            } else {
                response.sendRedirect("/loginform");
                return;
            }
        } else {
            response.sendRedirect("/loginform");
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }

    }

    @Override
    public void destroy() {

    }

    private String validateTokenAndUserId(String token){
        return token; //토큰 자체를 사용자 id로 반환
    }


}
