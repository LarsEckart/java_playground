# my java playground

[![Test Status](../../workflows/test/badge.svg)](https://github.com/LarsEckart/java_playground/actions)
[![Known Vulnerabilities](https://snyk.io/test/github/larseckart/java_playground/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/larseckart/java_playground?targetFile=build.gradle)
[![Gradle Status](https://gradleupdate.appspot.com/LarsEckart/java_playground/status.svg)](https://gradleupdate.appspot.com/LarsEckart/java_playground/status)

This is a place for me to explore java core api and various libraries.

## jdeps

```
jdeps --class-path 'libs/*' -R build/libs/java_playground.jar
```

## Dependency graph

```
gradlew generateDependencyGraph
```

### GitHub Markdown Task Lists

- [x] use JDK 15
- [ ] try JDK 16 early access

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
