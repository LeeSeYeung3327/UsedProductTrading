package com.dcu.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorization -> authorization
                .requestMatchers("/images/*", "/upload/images/**", "/","/memberSignUp", "/memberLogin").permitAll()
                .anyRequest().authenticated()
        );
        httpSecurity.formLogin(form->form
                .loginPage("/memberLogin")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/productList")
                .permitAll()
        );

        httpSecurity.logout(logout->logout
                .logoutSuccessUrl("/productList")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // 대표적인 해싱 기법
    }
}