package com.csc483.test;

/**
 * Lightweight assertion helpers used by both test classes.
 * Throws AssertionError (a standard JVM error) on failure, same as JUnit does.
 * No external dependencies required.
 */
public class Assert {

    public static void assertEquals(int expected, int actual, String message) {
        if (expected != actual)
            fail(message + " => expected " + expected + " but got " + actual);
    }

    public static void assertEquals(long expected, long actual, String message) {
        if (expected != actual)
            fail(message + " => expected " + expected + " but got " + actual);
    }

    public static void assertEquals(double expected, double actual, double delta, String message) {
        if (Math.abs(expected - actual) > delta)
            fail(message + " => expected " + expected + " but got " + actual);
    }

    public static void assertEquals(String expected, String actual, String message) {
        if (!expected.equals(actual))
            fail(message + " => expected \"" + expected + "\" but got \"" + actual + "\"");
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual))
            fail(message + " => expected " + expected + " but got " + actual);
    }

    public static void assertArrayEquals(int[] expected, int[] actual, String message) {
        if (expected.length != actual.length)
            fail(message + " => length mismatch: " + expected.length + " vs " + actual.length);
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i])
                fail(message + " => mismatch at index " + i + ": expected " + expected[i] + " got " + actual[i]);
        }
    }

    public static void assertNotNull(Object obj, String message) {
        if (obj == null) fail(message + " => expected non-null but got null");
    }

    public static void assertNull(Object obj, String message) {
        if (obj != null) fail(message + " => expected null but got " + obj);
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) fail(message);
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) fail(message + " => expected false but was true");
    }

    public static void assertThrows(Class<? extends Exception> expected, Runnable action, String message) {
        try {
            action.run();
            fail(message + " => expected " + expected.getSimpleName() + " but no exception was thrown");
        } catch (Exception e) {
            if (!expected.isInstance(e))
                fail(message + " => expected " + expected.getSimpleName() + " but got " + e.getClass().getSimpleName());
        }
    }

    private static void fail(String message) {
        throw new AssertionError(message);
    }
}
