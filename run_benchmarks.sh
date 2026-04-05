#!/bin/bash
# Runs both benchmark programs.
# Compile first with ./compile.sh

set -e

mkdir -p datasets

echo "Running Question 1 - TechMart Search Benchmark..."
java -cp out com.csc483.assignment1.search.TechMartBenchmark

echo ""
echo "Running Question 2 - Sorting Algorithms Benchmark..."
java -cp out com.csc483.assignment2.sorting.SortingBenchmark
