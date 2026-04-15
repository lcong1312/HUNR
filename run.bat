@echo off
chcp 65001 >nul
setlocal

cd /d "%~dp0"

echo ========================================
echo [1/3] Clean + Package project...
echo ========================================
call .\mvnw.cmd clean package
if errorlevel 1 (
    echo.
    echo [ERROR] Build failed. Please check the logs above.
    pause
    exit /b 1
)

set "JAR_PATH=target\HunrProvision-0.0.1-SNAPSHOT.jar"
if not exist "%JAR_PATH%" (
    echo.
    echo [ERROR] Jar not found: %JAR_PATH%
    pause
    exit /b 1
)

echo.
echo ========================================
echo [2/3] Starting server...
echo ========================================
java -jar "%JAR_PATH%"

echo.
echo ========================================
echo [3/3] Server stopped.
echo ========================================
pause
endlocal
