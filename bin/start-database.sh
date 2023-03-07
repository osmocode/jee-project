#!/bin/bash -e

# Authorize the execution of this script from anywhere
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "$DIR/.."

java -cp ./lib/h2-1.4.200.jar org.h2.tools.Server -ifNotExists
