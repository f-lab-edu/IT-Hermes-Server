package com.hermes.ithermes.presentation.security;

import com.hermes.ithermes.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
    private final UserService userService;

    @Value("${springboot.jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests().requestMatchers("/user/login", "/user/join","/user/duplicate-id","/user/duplicate-nickname","/user/refresh-token").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/user/login", "/api/user/join","/api/user/duplicate-id","/api/user/duplicate-nickname","/api/user/refresh-token").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/youtube-and-news/", "/job/","/crawling-contents-last-title/","/api/alarm/recommend").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/youtube-and-news/", "/api/job/","/api/crawling-contents-last-title/","/api/alarm/subscription").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/contents/main","/url-record/","/contents/count").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/contents/main","/api/url-record/","/api/contents/count","/api/contents/search","/api/contents/refreshCache","/api/contents/elasticsearch").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/**").authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/favicon.ico");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:5500");
                registry.addMapping("/**").allowedOrigins("http://127.0.0.1:5500");
                registry.addMapping("/**").allowedOrigins("http://127.0.0.1:80");
                registry.addMapping("/**").allowedOrigins("http://localhost:80");
                registry.addMapping("/**").allowedMethods(ALLOWED_METHOD_NAMES.split(","));
            }
        };
    }
}
