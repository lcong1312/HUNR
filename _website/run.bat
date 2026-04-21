@echo off
chcp 65001 >nul
setlocal EnableExtensions

cd /d "%~dp0"

set "MODE=%~1"
if "%MODE%"=="" set "MODE=dev"
set "PAUSE_ON_ERROR=1"
if /I "%~2"=="--no-pause" set "PAUSE_ON_ERROR=0"

if /I "%MODE%"=="dev" goto :run_dev
if /I "%MODE%"=="build" goto :run_build
if /I "%MODE%"=="start" goto :run_start
if /I "%MODE%"=="help" (
    call :usage
    exit /b 0
)

call :fail "Unknown mode: %MODE%"
call :usage
exit /b 1

:usage
echo Usage:
echo   run.bat dev    ^(default, run Next.js dev on port 5000^)
echo   run.bat build  ^(build production files^)
echo   run.bat start  ^(start production server on port 5000^)
echo   run.bat help
echo.
echo Tip: add --no-pause if you run from terminal and do not want pause on errors.
exit /b 0

:pause_if_needed
if "%PAUSE_ON_ERROR%"=="1" pause
exit /b 0

:fail
echo.
echo [ERROR] %~1
call :pause_if_needed
exit /b 1

:check_node
where node >nul 2>&1
if errorlevel 1 (
    call :fail "Node.js was not found in PATH."
    exit /b 1
)

where npm >nul 2>&1
if errorlevel 1 (
    call :fail "npm was not found in PATH."
    exit /b 1
)

set "NODE_MAJOR="
set "NODE_MINOR="
for /f "tokens=1,2 delims=." %%a in ('node -v 2^>nul') do (
    set "NODE_MAJOR=%%a"
    set "NODE_MINOR=%%b"
)
set "NODE_MAJOR=%NODE_MAJOR:v=%"

if not defined NODE_MAJOR (
    call :fail "Cannot detect Node.js version."
    exit /b 1
)

if %NODE_MAJOR% LSS 18 (
    call :fail "Node.js v18.17.0 or newer is required. Current: v%NODE_MAJOR%.%NODE_MINOR%"
    exit /b 1
)

if %NODE_MAJOR% EQU 18 if %NODE_MINOR% LSS 17 (
    call :fail "Node.js v18.17.0 or newer is required. Current: v%NODE_MAJOR%.%NODE_MINOR%"
    exit /b 1
)

exit /b 0

:ensure_deps
if not exist "package.json" (
    call :fail "package.json not found in %cd%"
    exit /b 1
)

if not exist "node_modules" (
    echo Installing dependencies...
    call npm install
    if errorlevel 1 (
        call :fail "npm install failed."
        exit /b 1
    )
)
exit /b 0

:run_dev
call :check_node || exit /b 1
call :ensure_deps || exit /b 1
echo Starting website dev server on http://localhost:5000 ...
call npm run dev
exit /b %errorlevel%

:run_build
call :check_node || exit /b 1
call :ensure_deps || exit /b 1
echo Building website...
call npm run build
exit /b %errorlevel%

:run_start
call :check_node || exit /b 1
call :ensure_deps || exit /b 1

if not exist ".next" (
    echo .next folder not found, running build first...
    call npm run build
    if errorlevel 1 exit /b 1
)

echo Starting website production server on http://localhost:5000 ...
call npm run start
exit /b %errorlevel%
