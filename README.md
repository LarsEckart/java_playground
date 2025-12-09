# Java Playground

[![Test Status](https://github.com/LarsEckart/java_playground/actions/workflows/test.yml/badge.svg)](https://github.com/LarsEckart/java_playground/actions/workflows/test.yml)
[![Known Vulnerabilities](https://snyk.io/test/github/larseckart/java_playground/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/larseckart/java_playground?targetFile=build.gradle)

Personal sandbox for exploring Java language features and libraries.

## Advent of Code inputs

- Puzzle inputs are **not** tracked in git (`src/test/resources/advent*/**` is ignored).
- Advent 2025 tests auto-download inputs locally using `AOC_SESSION` (session cookie) and `AOC_USER_AGENT`; downloads are blocked on CI and those tests are JUnit-disabled when `CI`/`GITHUB_ACTIONS` is set.
- Suggested user agent: `java_playground/2025 (maintainer: you@example.com)`.

## Useful commands

```
# Dependency analysis
jdeps --class-path 'libs/*' -R build/libs/java_playground.jar

# Dependency graph
./gradlew generateDependencyGraph
```
