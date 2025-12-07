param (
    [string]$InputXml = "target/spotbugsXml.xml",
    [string]$OutputJson = "gl-sast-report.json"
)

# Load XML
[xml]$xml = Get-Content $InputXml

# Prepare SARIF structure
$sarif = @{
    version = "2.1.0"
    runs = @(@{
        tool = @{
            driver = @{
                name = "SpotBugs"
            }
        }
        results = @()
    })
}

foreach ($bug in $xml.BugCollection.BugInstance) {
    $sourceLine = $bug.SourceLine
    if (-not $sourceLine) { continue }

    $path = $sourceLine.sourcepath
    if (-not $path) { continue }

    $startLine = [int]($sourceLine.start | ForEach-Object { $_ })
    if (-not $startLine -or $startLine -lt 1) { $startLine = 1 }
    $endLine = [int]($sourceLine.end | ForEach-Object { $_ })
    if (-not $endLine -or $endLine -lt $startLine) { $endLine = $startLine }

    $result = @{
        ruleId = $bug.type
        level  = "warning"
        message = @{
            text = $bug.ShortMessage
        }
        locations = @(@{
            physicalLocation = @{
                artifactLocation = @{ uri = $path }
                region = @{ startLine = $startLine; endLine = $endLine }
            }
        })
    }

    $sarif.runs[0].results += $result
}

# Save as JSON
$sarif | ConvertTo-Json -Depth 5 | Out-File -Encoding UTF8 $OutputJson

Write-Host "SARIF report generated: $OutputJson"
