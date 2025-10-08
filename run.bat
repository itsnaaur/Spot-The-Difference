@echo off
REM Windows batch script to build and run the Spot the Difference game

echo ========================================
echo Spot the Difference Game - Launcher
echo ========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven from https://maven.apache.org/
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 11+ from https://openjdk.org/
    pause
    exit /b 1
)

echo [1/3] Checking Java version...
java -version
echo.

echo [2/3] Building project with Maven...
call mvn clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo [3/3] Launching game...
echo.
java -jar target\SpotTheDifference.jar

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Failed to launch game!
    pause
    exit /b 1
)

echo.
echo Game closed successfully!
pause

