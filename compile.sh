#!/bin/bash
# Compiles all source files (main + test) into out/

set -e

SRC_MAIN="src/main/java"
SRC_TEST="src/test/java"
OUT="out"

mkdir -p "$OUT"

echo "Compiling main sources..."
find "$SRC_MAIN" -name "*.java" | xargs javac -d "$OUT" -sourcepath "$SRC_MAIN"

echo "Compiling test sources..."
find "$SRC_TEST" -name "*.java" | xargs javac -cp "$OUT" -d "$OUT" -sourcepath "$SRC_TEST"

echo ""
echo "Compilation complete. All classes written to out/"
