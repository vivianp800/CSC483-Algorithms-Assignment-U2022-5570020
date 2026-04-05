# CSC483-Algorithms-Assignment-U20225570020

**Student:** Peters Vivian Okpokipoy
**ID:** U2022/5570020
**Course:** CSC 483.1 - Algorithms Analysis and Design  
**Session:** 2025/2026, First Semester

---

## Requirements

- Java 11 or higher (just the JDK, nothing else)

Check your Java version with:
```
java -version
javac -version
```

---

## Project Structure

```
├── compile.sh              - compiles everything
├── run_tests.sh            - runs all tests
├── run_benchmarks.sh       - runs both benchmark programs
├── datasets/               - sample input files committed to the repo
│   ├── sort_random_1000.txt
│   ├── sort_sorted_1000.txt
│   ├── sort_reverse_1000.txt
│   ├── sort_nearly_1000.txt
│   └── sort_dupes_1000.txt
└── src/
    ├── main/java/com/csc483/
    │   ├── assignment1/search/
    │   │   ├── Product.java
    │   │   ├── SearchEngine.java
    │   │   ├── DataGenerator.java
    │   │   └── TechMartBenchmark.java
    │   └── assignment2/sorting/
    │       ├── Sorter.java
    │       ├── InsertionSort.java
    │       ├── MergeSort.java
    │       ├── QuickSort.java
    │       ├── SortingDataGenerator.java
    │       └── SortingBenchmark.java
    └── test/java/com/csc483/
        ├── test/
        │   ├── Assert.java       - assertion helpers (replaces JUnit assertions)
        │   ├── TestRunner.java   - runs test methods by reflection
        │   └── RunAllTests.java  - entry point that runs all suites
        ├── assignment1/
        │   └── SearchEngineTest.java
        └── assignment2/
            └── SortingAlgorithmsTest.java
```

---

## Step 1: Compile

On Linux/Mac:
```bash
chmod +x compile.sh run_tests.sh run_benchmarks.sh
./compile.sh
```

On Windows (Command Prompt — enable delayed expansion first):
```bat
mkdir out
for /r src\main\java %%f in (*.java) do javac -d out "%%f"
for /r src\test\java %%f in (*.java) do javac -cp out -d out "%%f"
```

Or manually on any OS:
```bash
find src/main/java -name "*.java" | xargs javac -d out
find src/test/java -name "*.java" | xargs javac -cp out -d out
```

---

## Step 2: Run the Tests

```bash
./run_tests.sh
```

Or manually:
```bash
java -cp out com.csc483.test.RunAllTests
```

---

## Step 3: Run the Benchmarks

```bash
./run_benchmarks.sh
```

Or individually:
```bash
java -cp out com.csc483.assignment1.search.TechMartBenchmark
java -cp out com.csc483.assignment2.sorting.SortingBenchmark
```

Running `TechMartBenchmark` also generates `datasets/products_100k.csv`.
---


## How the Test Framework Works

There is no JUnit or any third-party library. The test framework is three small files inside `src/test/java/com/csc483/test/`:

- `Assert.java` provides `assertEquals`, `assertNotNull`, `assertNull`, `assertTrue`, `assertThrows`, and `assertArrayEquals`. Each throws a standard Java `AssertionError` on failure, the same way JUnit does internally.
- `TestRunner.java` uses Java reflection to find every method whose name starts with `test`, calls `setUp()` before each one, runs it, and prints PASS or FAIL with the failure message.
- `RunAllTests.java` is the entry point that creates one instance of each test class and calls `run()`.

No annotations, no external jars, nothing to install.

---

## Known Limitations

- `addProduct` uses array shifting (O(n) per insert). For write-heavy workloads a `TreeMap` would be more efficient.
- Benchmark timings vary between runs due to JVM warm-up. The numbers are most reliable at large input sizes (n = 100,000).
- The products dataset uses randomly generated IDs so duplicates are possible before the array is sorted.
