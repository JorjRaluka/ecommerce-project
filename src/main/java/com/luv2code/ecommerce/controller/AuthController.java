package com.luv2code.ecommerce.controller;

import com.luv2code.ecommerce.dao.CustomerRepository;
import com.luv2code.ecommerce.dto.LoginRequest;
import com.luv2code.ecommerce.dto.LoginResponse;
import com.luv2code.ecommerce.dto.RegisterRequest;
import com.luv2code.ecommerce.entity.Customer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private CustomerRepository customerRepo;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (customerRepo.findByEmail(request.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
        }

        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getEmail().equalsIgnoreCase("admin@yahoo.com")) {
            customer.setRole("ADMIN");
        } else {
            customer.setRole("USER");
        }

        customerRepo.save(customer);
        return ResponseEntity.ok("Registration successful! You can now log in.");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Customer customer = customerRepo.findByEmail(request.getEmail());

        if (customer == null || !passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        return ResponseEntity.ok(new LoginResponse(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getRole()
        ));
    }

}
