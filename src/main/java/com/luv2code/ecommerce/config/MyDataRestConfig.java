package com.luv2code.ecommerce.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.ecommerce.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;
   private EntityManager entityManager;
   @Autowired
   public MyDataRestConfig(EntityManager theEntityManager){
       entityManager=theEntityManager;
   }
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE,HttpMethod.PATCH};

        //disable HTTP put,post and delete.Read only(GET)
        disableHttpMethod(Product.class,config, theUnsupportedActions);
        disableHttpMethod(ProductCategory.class,config, theUnsupportedActions);
        disableHttpMethod(Country.class,config, theUnsupportedActions);
        disableHttpMethod(State.class,config, theUnsupportedActions);
        disableHttpMethod(Order.class,config, theUnsupportedActions);


        //call an internal helper method
        exposeIds(config);
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
        cors.addMapping("/api/**").allowedOrigins("http://localhost:4200");


    }

    private static void disableHttpMethod(Class theClass,RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config){
            Set<EntityType<?>> entities=entityManager.getMetamodel().getEntities();
            List<Class> entityClasses=new ArrayList<>();
                for(EntityType temp:entities){
                entityClasses.add(temp.getJavaType());
            }
                Class[]domainTypes=entityClasses.toArray(new Class[0]);
                config.exposeIdsFor(domainTypes);

        }
    }







