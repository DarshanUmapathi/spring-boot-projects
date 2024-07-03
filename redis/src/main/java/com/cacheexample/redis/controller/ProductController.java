package com.cacheexample.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cacheexample.redis.model.Product;
import com.cacheexample.redis.service.Productservice;

@RestController
public class ProductController {
	
	@Autowired
    private Productservice productService;

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

}
