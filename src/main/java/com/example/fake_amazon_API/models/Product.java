package com.example.fake_amazon_API.models;

import org.springframework.data.annotation.Id;

public class Product {
    @Id
    private String id;
    private String title;
    private double price;
    private String desc;
    private String category;
    private int quantity;
    private boolean best;
    private String image;

    public Product() {
    }

    public Product(String id, String title, double price, String desc, String category, int quantity, boolean best, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.desc = desc;
        this.category = category;
        this.quantity = quantity;
        this.best = best;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isBest() {
        return best;
    }

    public void setBest(boolean best) {
        this.best = best;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", desc='" + desc + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", best=" + best +
                ", image='" + image + '\'' +
                '}';
    }
}
