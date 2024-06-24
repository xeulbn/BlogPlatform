package org.example.blogplatform.config;

import org.example.blogplatform.filter.AuthenticationFilter;
import org.example.blogplatform.service.UserService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean <AuthenticationFilter> authenticationFilter(
            UserService userService
    ) {
        FilterRegistrationBean <AuthenticationFilter> registrationBean = new FilterRegistrationBean <>();
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setUserService(userService);
        registrationBean.setFilter(authenticationFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;

    }
}
