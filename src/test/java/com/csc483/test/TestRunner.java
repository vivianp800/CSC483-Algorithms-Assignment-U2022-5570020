package com.csc483.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Minimal test runner. Subclasses call run() from their main method.
 * Each public method whose name starts with "test" is treated as a test case.
 */
public abstract class TestRunner {

    private int passed = 0;
    private int failed = 0;
    private final List<String> failures = new ArrayList<>();

    protected void run() {
        String className = getClass().getSimpleName();
        System.out.println("=".repeat(60));
        System.out.println("Running: " + className);
        System.out.println("=".repeat(60));

        Method[] methods = getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (!m.getName().startsWith("test")) continue;
            try {
                // Each test gets a fresh instance state via setUp
                setUp();
                m.invoke(this);
                System.out.println("  PASS  " + m.getName());
                passed++;
            } catch (java.lang.reflect.InvocationTargetException e) {
                Throwable cause = e.getCause();
                String msg = "  FAIL  " + m.getName() + "\n         " + cause.getMessage();
                System.out.println(msg);
                failures.add(msg);
                failed++;
            } catch (Exception e) {
                String msg = "  ERROR " + m.getName() + "\n         " + e.getMessage();
                System.out.println(msg);
                failures.add(msg);
                failed++;
            }
        }

        System.out.println("-".repeat(60));
        System.out.println("Results: " + passed + " passed, " + failed + " failed");
        if (!failures.isEmpty()) {
            System.out.println("\nFailed tests:");
            failures.forEach(System.out::println);
        }
        System.out.println("=".repeat(60));
        System.out.println();
    }

    // Subclasses override this to re-initialise state before each test.
    protected void setUp() {}
}
