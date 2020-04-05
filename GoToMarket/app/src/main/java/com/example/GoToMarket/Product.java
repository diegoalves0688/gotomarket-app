package com.example.GoToMarket;

public class Product {

    private String name;

    private Long price;

    private Long quantity;

    private String imageUrl;

    public Product(String name, Long price, Long quantity, String imageUrl) {
        this.setName(name);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setImageUrl(imageUrl);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
