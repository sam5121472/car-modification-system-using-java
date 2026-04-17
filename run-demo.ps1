$ErrorActionPreference = "Stop"

function Get-JdkRoot {
    $candidates = @(
        $env:JAVA_HOME,
        "C:\Program Files\Java\latest",
        "C:\Program Files\Java\jdk-26"
    ) | Where-Object { $_ -and (Test-Path $_) } | Select-Object -Unique

    foreach ($candidate in $candidates) {
        $javaPath = Join-Path $candidate "bin\java.exe"
        if (Test-Path $javaPath) {
            return $candidate
        }
    }

    throw "No JDK was found. Install Java or set JAVA_HOME before running the project."
}

& (Join-Path $PSScriptRoot "build.ps1")

$jdkRoot = Get-JdkRoot
$javaPath = Join-Path $jdkRoot "bin\java.exe"
$outputDirectory = Join-Path $PSScriptRoot "out"

& $javaPath -cp $outputDirectory com.cms.app.Main demo
