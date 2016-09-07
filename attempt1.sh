#!/bin/bash -x
./gradlew testClasses
echo '// change' >> project2/build.gradle
./gradlew testClasses
./gradlew check
