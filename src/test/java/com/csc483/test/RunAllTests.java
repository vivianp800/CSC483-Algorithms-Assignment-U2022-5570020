package com.csc483.test;

import com.csc483.assignment1.SearchEngineTest;
import com.csc483.assignment2.SortingAlgorithmsTest;

/**
 * Entry point that runs all test suites.
 * Compile and run with the provided scripts (compile.sh / run_tests.sh),
 * or see README.md for the manual javac/java commands.
 */
public class RunAllTests {

    public static void main(String[] args) {
        new SearchEngineTest().run();
        new SortingAlgorithmsTest().run();
    }
}
