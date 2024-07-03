package com.cacheexample.redis.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.cacheexample.redis.model.Product;
import com.cacheexample.redis.repository.ProductRepository;

@Service
public class Productservice {
	
	
	 private static final Logger logger = LoggerFactory.getLogger(Productservice.class);

	    @Autowired
	    private ProductRepository productRepository;

	    @Autowired
	    private RedisTemplate<String, Object> redisTemplate;

	    private static final String PRODUCT_CACHE_PREFIX = "product:";

	    public Product getProductById(Long id) {
	        String cacheKey = PRODUCT_CACHE_PREFIX + id;
	        Product product = (Product) redisTemplate.opsForValue().get(cacheKey);

	        if (product == null) {
	            logger.info("Cache miss for key: " + cacheKey);
	            Optional<Product> productOptional = productRepository.findById(id);
	            if (productOptional.isPresent()) {
	                product = productOptional.get();
	                redisTemplate.opsForValue().set(cacheKey, product);
	            } else {
	                throw new RuntimeException("Product not found");
	            }
	        } else {
	            logger.info("Cache hit for key: " + cacheKey + ", Value: " + product);
	        }

	        return product;
	    }
	    
}
