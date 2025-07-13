@echo off
set VERSION=0.15
set MESSAGE="deploying in another instance in 90 port ignoring host Error: few only running not all"

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
    docker build -t chandujinka/naakiranab%VERSION% .
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
    docker push chandujinka/naakiranab%VERSION%
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

gcloud compute instances update-container test --zone=us-central1-a --container-image=chandujinka/naakiranab%VERSION%

pause
