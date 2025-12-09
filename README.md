# my java playground

[![Test Status](https://github.com/LarsEckart/java_playground/actions/workflows/test.yml/badge.svg)](https://github.com/LarsEckart/java_playground/actions/workflows/test.yml)
[![Known Vulnerabilities](https://snyk.io/test/github/larseckart/java_playground/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/larseckart/java_playground?targetFile=build.gradle)
[![Gradle Status](https://gradleupdate.appspot.com/LarsEckart/java_playground/status.svg)](https://gradleupdate.appspot.com/LarsEckart/java_playground/status)

This is a place for me to explore java core api and various libraries.

## Advent of Code inputs

- Puzzle inputs are **not** tracked in git (`src/test/resources/advent*/**` is ignored).
- Advent 2025 tests auto-download inputs locally using `AOC_SESSION` (session cookie) and `AOC_USER_AGENT`; downloads are blocked on CI and those tests are JUnit-disabled when `CI`/`GITHUB_ACTIONS` is set.
- Suggested user agent: `java_playground/2025 (maintainer: you@example.com)`.

## jdeps

```
jdeps --class-path 'libs/*' -R build/libs/java_playground.jar
```

## Dependency graph

```
gradlew generateDependencyGraph
```

### GitHub Markdown Task Lists

- [x] use JDK 17
- [ ] try JDK 21 early access

### License

```
Copyright 2023 Lars Eckart

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
