package com.luv2code.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer->
                configurer.requestMatchers("/api/orders/**").authenticated()
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
