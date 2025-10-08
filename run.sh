#!/bin/bash
# Unix/Linux/Mac script to build and run the Spot the Difference game

echo "========================================"
echo "Spot the Difference Game - Launcher"
echo "========================================"
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo "Please install Maven from https://maven.apache.org/"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java 11+ from https://openjdk.org/"
    exit 1
fi

echo "[1/3] Checking Java version..."
java -version
echo ""

echo "[2/3] Building project with Maven..."
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: Build failed!"
    exit 1
fi

echo ""
echo "[3/3] Launching game..."
echo ""
java -jar target/SpotTheDifference.jar

if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: Failed to launch game!"
    exit 1
fi

echo ""
echo "Game closed successfully!"

