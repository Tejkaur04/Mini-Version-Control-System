@echo off
:: Move to the script's directory (project root)
cd /d "%~dp0"

:: Ensure output directory exists
if not exist "out" (
    mkdir out
)

:: Compile all Java source files
javac -d out/ src/vcs/Main.java src/vcs/core/*.java src/vcs/datastructures/*.java src/vcs/util/*.java

:: Stop if compilation fails
if errorlevel 1 (
    echo Compilation failed.
    exit /b 1
)

:: Run the Java program with arguments
java -cp out vcs.Main %*
