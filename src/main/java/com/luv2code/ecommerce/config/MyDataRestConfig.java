package com.luv2code.ecommerce.config;

import com.luv2code.ecommerce.dao.Product;
import com.luv2code.ecommerce.dao.ProductCategory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

//        //disable HTTP put,post and delete.Read only(GET)
//        config.getExposureConfiguration()
//                .forDomainType(Product.class);
//
//        config.getExposureConfiguration()
//                .forDomainType(ProductCategory.class);


    }



}
