@echo off
:: Move to project root
cd /d "%~dp0"

:: Ensure the output directory exists
if not exist "out" (
    mkdir out
)

:: Compile Java files if needed
echo Compiling Java source files...
javac -d out/ src/vcs/Main.java src/vcs/core/*.java src/vcs/datastructures/*.java src/vcs/util/*.java

:: Stop if compilation fails
if errorlevel 1 (
    echo Compilation failed.
    exit /b 1
)

:: Run Java from current working directory (where user calls from)
:: Use %CD% to pass current working directory as a working repo path (optional enhancement)
java -cp out vcs.Main %*
