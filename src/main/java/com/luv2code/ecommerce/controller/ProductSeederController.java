package com.luv2code.ecommerce.controller;

import com.github.javafaker.Faker;
import com.luv2code.ecommerce.dao.ProductCategoryRepository;
import com.luv2code.ecommerce.dao.ProductRepository;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/dev")
@CrossOrigin("http://localhost:4200")
public class ProductSeederController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @PostMapping("/seed-products")
    public ResponseEntity<String> generateFakeProducts() {
        Optional<ProductCategory> optionalCategory = categoryRepository.findById(1L);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.badRequest().body("Category ID 1 not found");
        }

        ProductCategory category = optionalCategory.get();
        Faker faker = new Faker();
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Product p = new Product();
            p.setCategory(category);
            p.setName(faker.commerce().productName());
            p.setSku(UUID.randomUUID().toString());
            p.setDescription(faker.lorem().sentence());
            p.setUnitPrice(new BigDecimal(faker.commerce().price(1.0, 500.0)));
            p.setImageUrl("https://picsum.photos/seed/" + UUID.randomUUID() + "/200/200");
            p.setUnitsInStock(faker.number().numberBetween(1, 100));
            p.setActive(true);

            products.add(p);
        }

        productRepository.saveAll(products);
        return ResponseEntity.ok("✅fake products generated.");
    }
   



}
