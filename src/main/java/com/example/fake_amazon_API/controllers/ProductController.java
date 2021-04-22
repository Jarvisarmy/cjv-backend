package com.example.fake_amazon_API.controllers;


import com.example.fake_amazon_API.CustomizedResponse;
import com.example.fake_amazon_API.models.Product;
import com.example.fake_amazon_API.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@CrossOrigin(origins="*")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    // endpoint to that retrieves all the products
    @GetMapping("/products")
    public ResponseEntity getProducts() {
         var customizedResponse = new CustomizedResponse("a list of products",productService.getProducts());
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    // endpoint that retrieves a specific product
    @GetMapping("/products/{id}")
    public ResponseEntity getAProduct(@PathVariable("id") String id) {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse("Product with id "+id, Collections.singletonList(productService.getAProduct(id)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(),null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    // endpoint that retrieves all the products based on a provided product category.
    // /movies/cat?cat=clothes
    @GetMapping("/products/cat")
    public ResponseEntity getProductsByCategory(@RequestParam(value="cat", defaultValue="none") String cat) {
        CustomizedResponse customizedResponse = null;
        if (cat.equals("none")) {
            customizedResponse = new CustomizedResponse("A list of category",productService.getAllCategories());
        } else {
            customizedResponse = new CustomizedResponse("A list of products with the category: " + cat, productService.getProductsByCategory(cat));
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }
    @GetMapping("/products/bestsellers")
    public ResponseEntity getProductsByBest() {
        CustomizedResponse customizedResponse = new CustomizedResponse("A list of bestseller products",productService.getProductsByBest());
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }



    // endpoint that will delete an existing product in the database: with validation
    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteAMovie(@PathVariable("id") String id) {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse("Product with id "+id+" is deleted", Collections.singletonList(productService.deleteAProduct(id)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(customizedResponse,HttpStatus.OK);
    }

    // endpoint that will create products to be added
    @PostMapping(value="/products", consumes={
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity createProducts(@RequestBody Product product) {
        productService.insertIntoProducts(product);
        return new ResponseEntity(product,HttpStatus.OK);
    }

    // endpoint that will update an existing product with validation login
    @PutMapping(value="/products/{id}",consumes={
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity editProduct(@PathVariable("id") String id, @RequestBody Product newProduct) {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse("Product with ID: " + id + " was updated successfully", Collections.singletonList(productService.editProduct(id, newProduct)));

        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(),null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }


}
