@echo off
set VERSION=1.291
set MESSAGE="testWithDockerFile"


VERSION = "2.0"
MESSAGE = "create new from local"

:: Initialize error flag
set ERROR_FLAG=0

:: Function to simulate a "catch" block
:catch
echo [ERROR] %1
set ERROR_FLAG=0
goto :continue

:continue
echo Starting commit process...


:: "try" block for git commands
echo Staging changes...
git add .
if %ERRORLEVEL% NEQ 0 (
    call :catch "Git add failed."
) else (
    echo Changes staged.
    echo Committing changes...
    git commit -m %VERSION%%MESSAGE%
    if %ERRORLEVEL% NEQ 0 (
        call :catch "Git commit failed."
    ) else (
        echo Changes committed.
        echo Pushing changes to remote repository...
        git push origin main
        if %ERRORLEVEL% NEQ 0 (
            call :catch "Git push failed."
        ) else (
            echo Git push succeeded.
        )
    )
)

echo Starting Docker build process...

:: "try" block for building Docker image
if %ERROR_FLAG% EQU 0 (
    echo Building Docker image...
    docker build -t docker.io/chandujinka/naakiranab%VERSION% .
    if %ERRORLEVEL% NEQ 0 (
        call :catch "Docker build failed."
    ) else (
        echo Docker build succeeded.
    )
)

echo Starting Docker push process...

:: "try" block for pushing Docker image
if %ERROR_FLAG% EQU 0 (
    echo Pushing Docker image...
    docker push docker.io/chandujinka/naakiranab%VERSION%
    if %ERRORLEVEL% NEQ 0 (
        call :catch "Docker push failed."
    ) else (
        echo Docker push succeeded.
    )
)

:: Check if there were any errors
if %ERROR_FLAG% NEQ 0 (
    echo Build process encountered errors.
) else (
    echo Build process completed successfully.
)

gcloud run deploy springboottest --image docker.io/chandujinka/naakiranab%VERSION% --platform managed


pause
