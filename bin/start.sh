#!/bin/bash -e

# Authorize the execution of this script from anywhere
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "$DIR/.."

# mvn org.codehaus.mojo:exec-maven-plugin:exec@tailwind-dev & mvn spring-boot:run
mvn spring-boot:run