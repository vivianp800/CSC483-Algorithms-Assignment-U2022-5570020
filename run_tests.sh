#!/bin/bash
# Runs all test suites.
# Compile first with ./compile.sh

set -e

java -cp out com.csc483.test.RunAllTests
