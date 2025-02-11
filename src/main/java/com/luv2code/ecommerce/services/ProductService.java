package com.luv2code.ecommerce.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.ecommerce.controllers.ProductController;
import com.luv2code.ecommerce.dao.Product;
import com.luv2code.ecommerce.dto.ProductDTO;
import com.luv2code.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    private ProductDTO convertToDTO(Product product){
        return modelMapper.map(product, ProductDTO.class);
    }

    private Product converToDAO(ProductDTO productDTO){
        return modelMapper.map(productDTO, Product.class);
    }

    public List<ProductDTO> getAllProducts(){
        var allProducts = productRepository.findAll();
        var dawd =  allProducts.stream().map(product -> convertToDTO(product)).collect(Collectors.toList());
        return dawd;
    }
    public Product createProduct(ProductDTO product){
        Product product1=new Product();
        product1.setName(product.getName());
        product1.setCategory(product.getCategory());
        product1.setActive(product.isActive());
        product1.setDescription(product.getDescription());
        product1.setSku(product.getSku());
        product1.setDateCreated(product.getDateCreated());
        product1.setLastUpdated(product.getLastUpdated());
        product1.setImageUrl(product.getImageUrl());
        product1.setUnitPrice(product.getUnitPrice());
        product1.setUnitsInStock(product.getUnitsInStock());
        return productRepository.save(product1);
    }

}
