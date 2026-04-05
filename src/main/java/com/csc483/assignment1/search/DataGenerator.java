package com.csc483.assignment1.search;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class DataGenerator {

    private static final String[] CATEGORIES = {"Laptop", "Phone", "Tablet", "TV", "Camera", "Audio", "Gaming"};
    private static final Random RAND = new Random(42);

    public static Product[] generateProducts(int count) {
        Product[] products = new Product[count];
        for (int i = 0; i < count; i++) {
            int    id       = RAND.nextInt(200_000) + 1;
            String name     = "Product_" + id + "_" + i;
            String category = CATEGORIES[RAND.nextInt(CATEGORIES.length)];
            double price    = 10.0 + RAND.nextDouble() * 990.0;
            int    stock    = RAND.nextInt(500);
            products[i]     = new Product(id, name, category, price, stock);
        }
        return products;
    }

    // Writes the products array to a CSV file for the GitHub datasets folder.
    public static void writeCsv(Product[] products, String filePath) throws IOException {
        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write("productId,productName,category,price,stockQuantity\n");
            for (Product p : products) {
                fw.write(String.format("%d,%s,%s,%.2f,%d\n",
                        p.getProductId(), p.getProductName(),
                        p.getCategory(), p.getPrice(), p.getStockQuantity()));
            }
        }
    }

    public static Product[] sortedCopy(Product[] products) {
        Product[] copy = Arrays.copyOf(products, products.length);
        Arrays.sort(copy, (a, b) -> Integer.compare(a.getProductId(), b.getProductId()));
        return copy;
    }
}
