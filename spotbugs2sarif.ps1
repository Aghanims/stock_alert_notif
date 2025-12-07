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

    $startRaw = $sourceLine.start
    if ($startRaw -is [System.Array]) { $startRaw = $startRaw[0] }
    [int]$startLine = 1
    [void][int]::TryParse($startRaw, [ref]$startLine)
    if ($startLine -lt 1) { $startLine = 1 }

    $endRaw = $sourceLine.end
    if ($endRaw -is [System.Array]) { $endRaw = $endRaw[0] }
    [int]$endLine = $startLine
    [void][int]::TryParse($endRaw, [ref]$endLine)
    if ($endLine -lt $startLine) { $endLine = $startLine }

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
