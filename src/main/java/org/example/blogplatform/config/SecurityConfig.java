package org.example.blogplatform.config;

import lombok.RequiredArgsConstructor;
import org.example.blogplatform.security.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/loginform")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/welcome")
                        .permitAll()
                )
                .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )
                .sessionManagement(sessionManagement ->sessionManagement
                                .maximumSessions(2)
                                .maxSessionsPreventsLogin(true)
                )
                .userDetailsService(customUserDetailService)
                .csrf(csrf->csrf.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
