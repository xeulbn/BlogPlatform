package org.example.blogplatform.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.blogplatform.model.User;
import org.example.blogplatform.service.UserService;
import org.example.blogplatform.util.UserContext;

import java.io.IOException;

public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String auth = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("auth")) {
                        auth = cookie.getValue();
                        break;
                    }
                }
            }
            if(auth !=null) {
                User user = new User();
                user.setUsername(auth);

                UserContext.setUser(user);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }finally {
            UserContext.clear();
        }

    }
}
