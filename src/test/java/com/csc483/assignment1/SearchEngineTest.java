package com.csc483.assignment1;

import com.csc483.assignment1.search.Product;
import com.csc483.assignment1.search.SearchEngine;
import com.csc483.test.Assert;
import com.csc483.test.TestRunner;

public class SearchEngineTest extends TestRunner {

    private SearchEngine engine;
    private Product[]    sorted;

    public static void main(String[] args) {
        new SearchEngineTest().run();
    }

    @Override
    protected void setUp() {
        engine = new SearchEngine();
        sorted = new Product[]{
            new Product(10, "Alpha",   "Laptop",  999.00, 5),
            new Product(20, "Beta",    "Phone",   499.00, 10),
            new Product(30, "Gamma",   "Tablet",  299.00, 20),
            new Product(40, "Delta",   "TV",     1299.00, 3),
            new Product(50, "Epsilon", "Camera",  799.00, 7),
        };
        engine.buildNameIndex(sorted);
    }

    // ------------------------------------------------------------------ Product
    public void testProductConstructorStoresAllFields() {
        Product p = new Product(1, "Test", "Cat", 9.99, 3);
        Assert.assertEquals(1,      p.getProductId(),      "productId");
        Assert.assertEquals("Test", p.getProductName(),    "productName");
        Assert.assertEquals("Cat",  p.getCategory(),       "category");
        Assert.assertEquals(9.99,   p.getPrice(), 0.001,   "price");
        Assert.assertEquals(3,      p.getStockQuantity(),  "stockQuantity");
    }

    public void testProductRejectsBlankName() {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> new Product(1, "", "Cat", 9.99, 0),
                "blank name should throw");
    }

    public void testProductRejectsNullName() {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> new Product(1, null, "Cat", 9.99, 0),
                "null name should throw");
    }

    public void testProductRejectsNegativePrice() {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> new Product(1, "X", "Cat", -1.0, 0),
                "negative price should throw");
    }

    public void testProductRejectsNegativeStock() {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> new Product(1, "X", "Cat", 1.0, -1),
                "negative stock should throw");
    }

    public void testProductEqualityByIdOnly() {
        Product a = new Product(5, "A", "Cat", 1.0, 1);
        Product b = new Product(5, "B", "Other", 2.0, 2);
        Assert.assertEquals(a, b, "products with same id should be equal");
    }

    public void testProductToStringContainsId() {
        Product p = new Product(42, "Gadget", "Electronics", 199.99, 5);
        Assert.assertTrue(p.toString().contains("42"), "toString should contain id");
    }

    // ------------------------------------------------------------------ Sequential search
    public void testSequentialSearchFindsExistingProduct() {
        Product result = engine.sequentialSearchById(sorted, 30);
        Assert.assertNotNull(result, "should find product with id=30");
        Assert.assertEquals("Gamma", result.getProductName(), "name should be Gamma");
    }

    public void testSequentialSearchReturnsNullWhenMissing() {
        Assert.assertNull(engine.sequentialSearchById(sorted, 99), "missing id should return null");
    }

    public void testSequentialSearchFindsFirst() {
        Assert.assertNotNull(engine.sequentialSearchById(sorted, 10), "should find first element");
    }

    public void testSequentialSearchFindsLast() {
        Assert.assertNotNull(engine.sequentialSearchById(sorted, 50), "should find last element");
    }

    public void testSequentialSearchNullArrayReturnsNull() {
        Assert.assertNull(engine.sequentialSearchById(null, 10), "null array should return null");
    }

    public void testSequentialSearchEmptyArrayReturnsNull() {
        Assert.assertNull(engine.sequentialSearchById(new Product[0], 10), "empty array should return null");
    }

    // ------------------------------------------------------------------ Binary search
    public void testBinarySearchFindsMiddleElement() {
        Product result = engine.binarySearchById(sorted, 30);
        Assert.assertNotNull(result, "should find product with id=30");
        Assert.assertEquals(30, result.getProductId(), "id should be 30");
    }

    public void testBinarySearchFindsFirstElement() {
        Assert.assertNotNull(engine.binarySearchById(sorted, 10), "should find first element");
    }

    public void testBinarySearchFindsLastElement() {
        Assert.assertNotNull(engine.binarySearchById(sorted, 50), "should find last element");
    }

    public void testBinarySearchReturnsNullWhenMissing() {
        Assert.assertNull(engine.binarySearchById(sorted, 99), "missing id should return null");
    }

    public void testBinarySearchNullArrayReturnsNull() {
        Assert.assertNull(engine.binarySearchById(null, 10), "null array should return null");
    }

    public void testBinarySearchEmptyArrayReturnsNull() {
        Assert.assertNull(engine.binarySearchById(new Product[0], 10), "empty array should return null");
    }

    public void testBinaryAndSequentialAgreeOnAllIds() {
        int[] ids = {10, 20, 30, 40, 50, 99};
        for (int id : ids) {
            Product seq = engine.sequentialSearchById(sorted, id);
            Product bin = engine.binarySearchById(sorted, id);
            Assert.assertEquals(seq, bin, "seq and binary should agree on id=" + id);
        }
    }

    // ------------------------------------------------------------------ Name search
    public void testSearchByNameCaseInsensitive() {
        Assert.assertNotNull(engine.searchByName(sorted, "ALPHA"), "uppercase should work");
        Assert.assertNotNull(engine.searchByName(sorted, "alpha"), "lowercase should work");
        Assert.assertNotNull(engine.searchByName(sorted, "Alpha"), "mixed case should work");
    }

    public void testSearchByNameReturnsNullWhenMissing() {
        Assert.assertNull(engine.searchByName(sorted, "Nonexistent"), "missing name should return null");
    }

    public void testSearchByNameNullTargetReturnsNull() {
        Assert.assertNull(engine.searchByName(sorted, null), "null target should return null");
    }

    public void testSearchByNameNullArrayReturnsNull() {
        Assert.assertNull(engine.searchByName(null, "Alpha"), "null array should return null");
    }

    // ------------------------------------------------------------------ Hybrid search
    public void testHybridSearchFindsProduct() {
        Assert.assertNotNull(engine.hybridSearchByName("beta"), "should find Beta");
    }

    public void testHybridSearchCaseInsensitive() {
        Assert.assertNotNull(engine.hybridSearchByName("GAMMA"), "uppercase should work");
    }

    public void testHybridSearchReturnsNullWhenMissing() {
        Assert.assertNull(engine.hybridSearchByName("unknown_product"), "missing name should return null");
    }

    public void testHybridSearchNullReturnsNull() {
        Assert.assertNull(engine.hybridSearchByName(null), "null should return null");
    }

    // ------------------------------------------------------------------ addProduct
    public void testAddProductMaintainsSortedOrder() {
        Product newProd = new Product(25, "NewItem", "Laptop", 199.0, 5);
        Product[] result = engine.addProduct(sorted, newProd);

        Assert.assertEquals(sorted.length + 1, result.length, "length should increase by 1");
        for (int i = 0; i < result.length - 1; i++) {
            Assert.assertTrue(result[i].getProductId() <= result[i + 1].getProductId(),
                    "not sorted at index " + i);
        }
    }

    public void testAddProductInsertAtBeginning() {
        Product newProd = new Product(1, "First", "Laptop", 9.99, 1);
        Product[] result = engine.addProduct(sorted, newProd);
        Assert.assertEquals(1, result[0].getProductId(), "first element should be id=1");
    }

    public void testAddProductInsertAtEnd() {
        Product newProd = new Product(999, "Last", "Laptop", 9.99, 1);
        Product[] result = engine.addProduct(sorted, newProd);
        Assert.assertEquals(999, result[result.length - 1].getProductId(), "last element should be id=999");
    }

    public void testAddProductUpdatesNameIndex() {
        Product newProd = new Product(25, "UniqueNameXYZ", "Laptop", 199.0, 5);
        engine.addProduct(sorted, newProd);
        Assert.assertNotNull(engine.hybridSearchByName("UniqueNameXYZ"),
                "name index should include new product");
    }

    public void testAddProductNullThrows() {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> engine.addProduct(sorted, null),
                "null product should throw");
    }

    public void testAddProductNullArrayTreatedAsEmpty() {
        Product newProd = new Product(1, "Solo", "Cat", 1.0, 1);
        Product[] result = engine.addProduct(null, newProd);
        Assert.assertEquals(1, result.length, "result should have 1 element");
        Assert.assertEquals(1, result[0].getProductId(), "product should be at index 0");
    }
}
