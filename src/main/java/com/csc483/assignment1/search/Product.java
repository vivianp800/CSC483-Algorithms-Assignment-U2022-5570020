package com.csc483.assignment1.search;

public class Product {

    private final int productId;
    private final String productName;
    private final String category;
    private final double price;
    private final int stockQuantity;

    public Product(int productId, String productName, String category,
                   double price, int stockQuantity) {
        if (productName == null || productName.isBlank())
            throw new IllegalArgumentException("productName must not be blank");
        if (category == null || category.isBlank())
            throw new IllegalArgumentException("category must not be blank");
        if (price < 0)
            throw new IllegalArgumentException("price must not be negative");
        if (stockQuantity < 0)
            throw new IllegalArgumentException("stockQuantity must not be negative");

        this.productId    = productId;
        this.productName  = productName;
        this.category     = category;
        this.price        = price;
        this.stockQuantity = stockQuantity;
    }

    public int    getProductId()     { return productId; }
    public String getProductName()   { return productName; }
    public String getCategory()      { return category; }
    public double getPrice()         { return price; }
    public int    getStockQuantity() { return stockQuantity; }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name=%s, category=%s, price=%.2f, stock=%d}",
                productId, productName, category, price, stockQuantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        return productId == ((Product) o).productId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(productId);
    }
}
