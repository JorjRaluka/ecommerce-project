package com.luv2code.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer->
                configurer.requestMatchers("/api/orders/**").authenticated()
                        .requestMatchers("/api/status/ping").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/orders/**").authenticated()
                        .requestMatchers( "/api/dev/seed-products").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/dev/delete-products").permitAll()

                        .anyRequest().permitAll())

                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults())
                );
        http.cors(Customizer.withDefaults());
        http.setSharedObject(ContentNegotiationStrategy.class,
                       new HeaderContentNegotiationStrategy());

        //mesaj in body 401 Unauthorized
        Okta.configureResourceServer401ResponseBody(http);
        http.csrf(csrf -> csrf.disable());


        return http.build();
    }
}

//csrf -secure random token;needs to be unique ;by default is enabled ;performed checks on POST using cookies
