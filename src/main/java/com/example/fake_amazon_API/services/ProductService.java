package com.example.fake_amazon_API.services;

import com.example.fake_amazon_API.models.Product;
import com.example.fake_amazon_API.models.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> getProducts() {

        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String cat) {
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(cat));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }
    public List<Product> getProductsByBest() {
        Query query = new Query();
        query.addCriteria(Criteria.where("best").is(true));
        List<Product> products = mongoTemplate.find(query, Product.class);
        return products;
    }

    public void insertIntoProducts(Product product) {
        productRepository.insert(product);
    }

    public Optional<Product> getAProduct(String id) throws Exception {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new Exception("Product with "+id+" is not found");
        }

        return product;
    }

    public Product editProduct(String id, Product newProductData) throws Exception{
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new Exception("Product with "+id+" is not found");
        }
        product.get().setTitle(newProductData.getTitle());
        product.get().setDesc(newProductData.getDesc());
        product.get().setPrice(newProductData.getPrice());
        product.get().setCategory(newProductData.getCategory());
        product.get().setQuantity(newProductData.getQuantity());
        product.get().setBest(newProductData.isBest());
        product.get().setImage(newProductData.getImage());

        Product updateProduct = productRepository.save(product.get());
        return updateProduct;
    }
    public List<String> getAllCategories() {
        List<Product> products = productRepository.findAll();
        ArrayList<String> categories = new ArrayList<String>();
        for (int i = 0; i<products.size(); i++) {
            categories.add(products.get(i).getCategory());
        }

        HashSet<String> uniqueCategories = new HashSet<String>(categories);
        List<String> unique = new ArrayList<String>(uniqueCategories);
        return unique;

    }


    public Optional<Product> deleteAProduct(String id) throws Exception{
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new Exception("Product with "+id+" is not found");
        }
        productRepository.deleteById(id);
        return product;

    }

}
