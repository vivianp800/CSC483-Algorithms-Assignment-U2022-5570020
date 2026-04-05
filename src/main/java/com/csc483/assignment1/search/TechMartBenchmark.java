package com.csc483.assignment1.search;

public class TechMartBenchmark {

    static final int N    = 100_000;
    static final int RUNS = 5;

    public static void main(String[] args) throws Exception {
        Product[] raw    = DataGenerator.generateProducts(N);
        Product[] sorted = DataGenerator.sortedCopy(raw);

        // Write dataset to file so it can be committed to the repository
        DataGenerator.writeCsv(sorted, "datasets/products_100k.csv");

        SearchEngine engine = new SearchEngine();
        engine.buildNameIndex(sorted);

        int bestId    = sorted[0].getProductId();
        int avgId     = sorted[N / 2].getProductId();
        int worstId   = 999_999_999;
        int middleId  = sorted[N / 2].getProductId();

        System.out.println("================================================================");
        System.out.println("TECHMART SEARCH PERFORMANCE ANALYSIS (n = 100,000 products)");
        System.out.println("================================================================");

        System.out.println("\nSEQUENTIAL SEARCH:");
        System.out.printf("  Best Case  (ID at position 0)  : %.3f ms%n", avgMs(() -> engine.sequentialSearchById(sorted, bestId)));
        System.out.printf("  Average Case (random ID)       : %.3f ms%n", avgMs(() -> engine.sequentialSearchById(sorted, avgId)));
        System.out.printf("  Worst Case (ID not found)      : %.3f ms%n", avgMs(() -> engine.sequentialSearchById(sorted, worstId)));

        System.out.println("\nBINARY SEARCH:");
        System.out.printf("  Best Case  (ID at middle)      : %.3f ms%n", avgMs(() -> engine.binarySearchById(sorted, middleId)));
        System.out.printf("  Average Case (random ID)       : %.3f ms%n", avgMs(() -> engine.binarySearchById(sorted, avgId)));
        System.out.printf("  Worst Case (ID not found)      : %.3f ms%n", avgMs(() -> engine.binarySearchById(sorted, worstId)));

        double seqAvg = avgMs(() -> engine.sequentialSearchById(sorted, avgId));
        double binAvg = avgMs(() -> engine.binarySearchById(sorted, avgId));
        long   factor = binAvg > 0 ? Math.round(seqAvg / binAvg) : 0;
        System.out.printf("%nPERFORMANCE IMPROVEMENT: Binary search is ~%dx faster on average%n", factor);

        System.out.println("\nHYBRID NAME SEARCH:");
        String sampleName = sorted[N / 3].getProductName();
        System.out.printf("  Average search time : %.3f ms%n", avgMs(() -> engine.hybridSearchByName(sampleName)));

        Product fresh      = new Product(250_001, "NewGadget_X", "Laptop", 499.99, 10);
        long    insertStart = System.nanoTime();
        engine.addProduct(sorted, fresh);
        double  insertMs    = (System.nanoTime() - insertStart) / 1_000_000.0;
        System.out.printf("  Average insert time : %.3f ms%n", insertMs);
        System.out.println("================================================================");
    }

    static double avgMs(Runnable action) {
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            long t = System.nanoTime();
            action.run();
            total += System.nanoTime() - t;
        }
        return total / (double) RUNS / 1_000_000.0;
    }
}
