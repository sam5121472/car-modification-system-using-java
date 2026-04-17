$ErrorActionPreference = "Stop"

function Get-JdkRoot {
    $candidates = @(
        $env:JAVA_HOME,
        "C:\Program Files\Java\latest",
        "C:\Program Files\Java\jdk-26"
    ) | Where-Object { $_ -and (Test-Path $_) } | Select-Object -Unique

    foreach ($candidate in $candidates) {
        $javacPath = Join-Path $candidate "bin\javac.exe"
        if (Test-Path $javacPath) {
            return $candidate
        }
    }

    throw "No JDK was found. Install Java or set JAVA_HOME before building the project."
}

$projectRoot = $PSScriptRoot
$jdkRoot = Get-JdkRoot
$javacPath = Join-Path $jdkRoot "bin\javac.exe"
$outputDirectory = Join-Path $projectRoot "out"
$sourceDirectory = Join-Path $projectRoot "src"

New-Item -ItemType Directory -Force -Path $outputDirectory | Out-Null

$sourceFiles = @(
    Get-ChildItem -Recurse -Path $sourceDirectory -Filter *.java | ForEach-Object { $_.FullName }
)

if ($sourceFiles.Count -eq 0) {
    throw "No Java source files were found in the src folder."
}

& $javacPath -d $outputDirectory $sourceFiles

Write-Host "Build completed successfully."
