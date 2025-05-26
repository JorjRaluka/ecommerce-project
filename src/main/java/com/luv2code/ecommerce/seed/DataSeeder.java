//package com.luv2code.ecommerce.seed;
//
//import com.github.javafaker.Faker;
//import com.luv2code.ecommerce.dao.ProductCategoryRepository;
//import com.luv2code.ecommerce.dao.ProductRepository;
//import com.luv2code.ecommerce.entity.Product;
//import com.luv2code.ecommerce.entity.ProductCategory;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//@Component
//public class DataSeeder {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ProductCategoryRepository productCategoryRepository;
//
//    private final Faker faker = new Faker();
//
//    @PostConstruct
//    public void seedData() {
//        System.out.println("Starting product seeding...");
//
//        if (productRepository.count() > 0) {
//            System.out.println("Products already exist. Skipping seeding.");
//            return;
//        }
//
//        List<ProductCategory> categories = productCategoryRepository.findAll();
//
//        if (categories.isEmpty()) {
//            System.out.println("No categories found. Cannot seed products.");
//            return;
//        }
//
//        List<Product> batch = new ArrayList<>();
//        Random random = new Random();
//
//        for (int i = 0; i < 100; i++) {
//            Product product = new Product();
//            product.setCategory(categories.get(random.nextInt(categories.size())));
//            product.setSku(faker.bothify("SKU-#####"));
//            product.setName(faker.commerce().productName());
//            product.setDescription(faker.lorem().sentence());
//            product.setUnitPrice(new BigDecimal(faker.commerce().price()));
//            product.setImageUrl("https://picsum.photos/seed/" + UUID.randomUUID() + "/200/200");
//            product.setActive(true);
//            product.setUnitsInStock(faker.number().numberBetween(1, 100));
//
//            batch.add(product);
//
//            if (batch.size() == 1000) {
//                productRepository.saveAll(batch);
//                batch.clear();
//                System.out.println("Inserted: " + (i + 1));
//            }
//        }
//
//        if (!batch.isEmpty()) {
//            productRepository.saveAll(batch);
//        }
//
//        System.out.println("✅ Seeding complete!");
//    }
//}
