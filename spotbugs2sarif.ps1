param (
    [string]$InputXml = "target/spotbugsXml.xml",
    [string]$OutputJson = "gl-sast-report.json"
)

if (-not (Test-Path -LiteralPath $InputXml)) {
    throw "Input file '$InputXml' not found"
}

# Load XML safely; SpotBugs schema is simple enough for direct cast
[xml]$xml = Get-Content -LiteralPath $InputXml

$artifactMap = @{}
$ruleMap = @{}

# Preload BugPattern metadata so we can emit driver rules
foreach ($pattern in @($xml.BugCollection.BugPattern)) {
    $id = $pattern.type
    if (-not $id) { continue }
    if ($ruleMap.ContainsKey($id)) { continue }

    $ruleIndex = $ruleMap.Count
    $ruleMap[$id] = $ruleIndex
}

$run = [pscustomobject]@{
    tool = [pscustomobject]@{
        driver = [pscustomobject]@{
            name  = "SpotBugs"
            rules = @()
        }
    }
    artifacts = @()
    results   = @()
}

foreach ($patternId in $ruleMap.Keys) {
    $run.tool.driver.rules += [pscustomobject]@{
        id          = $patternId
        name        = $patternId
        shortDescription = @{ text = $patternId }
    }
}

foreach ($bug in @($xml.BugCollection.BugInstance)) {
    $sourceLine = @($bug.SourceLine)[0]
    if (-not $sourceLine) { continue }

    $path = @($sourceLine.sourcepath)[0]
    if ([string]::IsNullOrWhiteSpace($path)) { continue }

    # Normalize path: take the first token and convert to forward slashes
    $path = ($path -split '\s+')[0]
    $path = $path -replace '\\', '/'

    if ($artifactMap.ContainsKey($path)) {
        $artifactIndex = $artifactMap[$path]
    } else {
        $run.artifacts += [pscustomobject]@{ location = [pscustomobject]@{ uri = $path } }
        $artifactIndex = $run.artifacts.Count - 1
        $artifactMap[$path] = $artifactIndex
    }

    $startRaw = @($sourceLine.start)[0]
    [int]$startLine = 1
    [void][int]::TryParse($startRaw, [ref]$startLine)
    if ($startLine -lt 1) { $startLine = 1 }

    $endRaw = @($sourceLine.end)[0]
    [int]$endLine = $startLine
    [void][int]::TryParse($endRaw, [ref]$endLine)
    if ($endLine -lt $startLine) { $endLine = $startLine }

    $priority = @($bug.priority)[0]
    switch ($priority) {
        1 { $level = "error" }
        2 { $level = "warning" }
        default { $level = "note" }
    }

    $ruleId = $bug.type
    if (-not $ruleId) { $ruleId = "SPOTBUGS" }
    $ruleIndex = $null
    if ($ruleMap.ContainsKey($ruleId)) { $ruleIndex = $ruleMap[$ruleId] }

    $run.results += [pscustomobject]@{
        ruleId    = $ruleId
        ruleIndex = $ruleIndex
        level     = $level
        message   = @{ text = $bug.ShortMessage }
        locations = @(
            [pscustomobject]@{
                physicalLocation = [pscustomobject]@{
                    artifactLocation = [pscustomobject]@{
                        uri   = $path
                        index = $artifactIndex
                    }
                    region = [pscustomobject]@{
                        startLine = $startLine
                        endLine   = $endLine
                    }
                }
            }
        )
    }
}

# Final SARIF document
$sarif = [pscustomobject]@{
    version = "2.1.0"
    runs    = @($run)
}

# Basic structural validation to catch schema issues early
foreach ($r in $sarif.runs) {
    foreach ($res in $r.results) {
        if (-not $res.locations -or -not ($res.locations[0].physicalLocation)) {
            throw "Result for rule '$($res.ruleId)' is missing a SARIF location object"
        }
    }
}

$sarif | ConvertTo-Json -Depth 15 | Set-Content -Path $OutputJson -Encoding utf8
Write-Host "SARIF report generated: $OutputJson"
