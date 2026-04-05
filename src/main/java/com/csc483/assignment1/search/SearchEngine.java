package com.csc483.assignment1.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SearchEngine {

    // HashMap index used by the hybrid name search
    private final Map<String, Product> nameIndex = new HashMap<>();

    // Returns the first product whose ID matches, or null if not found.
    public Product sequentialSearchById(Product[] products, int targetId) {
        if (products == null) return null;
        for (Product p : products) {
            if (p != null && p.getProductId() == targetId) return p;
        }
        return null;
    }

    // Requires the array to be sorted by productId in ascending order.
    public Product binarySearchById(Product[] products, int targetId) {
        if (products == null || products.length == 0) return null;
        int low = 0, high = products.length - 1;
        while (low <= high) {
            int mid   = low + (high - low) / 2;
            int midId = products[mid].getProductId();
            if      (midId == targetId) return products[mid];
            else if (midId  < targetId) low  = mid + 1;
            else                        high = mid - 1;
        }
        return null;
    }

    // Case-insensitive sequential name search (names are not sorted).
    public Product searchByName(Product[] products, String targetName) {
        if (products == null || targetName == null) return null;
        for (Product p : products) {
            if (p != null && p.getProductName().equalsIgnoreCase(targetName)) return p;
        }
        return null;
    }

    // Builds the HashMap index from the current products array.
    // Call this once after loading data, and after every addProduct.
    public void buildNameIndex(Product[] products) {
        nameIndex.clear();
        if (products == null) return;
        for (Product p : products) {
            if (p != null) nameIndex.put(p.getProductName().toLowerCase(), p);
        }
    }

    // O(1) average-case name lookup using the pre-built index.
    public Product hybridSearchByName(String targetName) {
        if (targetName == null) return null;
        return nameIndex.get(targetName.toLowerCase());
    }

    // Inserts newProduct into a sorted array while maintaining sort order.
    // Returns a new array of length products.length + 1.
    // Also updates the name index so it stays consistent.
    public Product[] addProduct(Product[] products, Product newProduct) {
        if (newProduct == null) throw new IllegalArgumentException("newProduct must not be null");
        if (products == null) products = new Product[0];

        int insertAt = products.length;
        for (int i = 0; i < products.length; i++) {
            if (products[i] != null && products[i].getProductId() > newProduct.getProductId()) {
                insertAt = i;
                break;
            }
        }

        Product[] result = new Product[products.length + 1];
        System.arraycopy(products, 0,        result, 0,           insertAt);
        result[insertAt] = newProduct;
        System.arraycopy(products, insertAt, result, insertAt + 1, products.length - insertAt);

        nameIndex.put(newProduct.getProductName().toLowerCase(), newProduct);
        return result;
    }

    // Sorts a products array in place by productId (used to prepare for binary search).
    public void sortById(Product[] products) {
        if (products == null) return;
        Arrays.sort(products, (a, b) -> Integer.compare(a.getProductId(), b.getProductId()));
    }
}
