#!/bin/bash -e

# Authorize the execution of this script from anywhere
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "$DIR/.."

# Colors
Reset=$'\e[0m' # Text Reset
Red=$'\e[0;31m'    # Red
Green=$'\e[0;32m'  # Green
Yellow=$'\e[0;33m' # Yellow
Purple=$'\e[0;35m' # Purple
Cyan=$'\e[0;36m'   # Cyan

echo -e "$Purple\nChecking dependencies...\n$Reset"

# Check if java is installed
if ! hash java 2> /dev/null; then
    echo "java:$Red ERROR - Java must be installed (see: https://www.java.com/fr/download/help/download_options.html#mac).$Reset" >&2
    exit 1
fi
echo "java:$Green OK !$Reset"

# Check if wrapper present
if [[ ! -d .mvn ]]; then
    echo "maven:$Red ERROR - Maven wrapper is missing.$Reset" >&2
# Check if maven is installed
elif ! hash mvn 2> /dev/null; then
    echo "maven:$Red ERROR - Maven must be installed (see: https://maven.apache.org/download.cgi).$Reset" >&2
    exit 1
fi
echo "maven:$Green OK !$Reset"

# Check if node is installed
if ! hash node 2> /dev/null; then
    echo "node:$Red ERROR - Node must be installed (see: https://nodejs.org/en/download/).$Reset" >&2
    exit 1
fi
echo "node:$Green OK !$Reset"

# Check if npm is installed
if ! hash npm 2> /dev/null; then
    echo "npm:$Red ERROR - Npm must be installed (see: https://docs.npmjs.com/cli/v7/configuring-npm/install).$Reset" >&2
    exit 1
fi
echo "npm:$Green OK !$Reset"

echo -e "$Purple\nInstall dependencies...\n$Reset"

npm install --silent
if [ $? -ne 0 ]; then
    echo "npm install:$Red ERROR - Install failed please contact admin system...$Reset" >&2
    exit 1
fi
echo -e "npm install:$Green SUCCEED !$Reset"

./mvnw clean install >/dev/null 2>/dev/null
if [ $? -ne 0 ]; then
    echo "mvn install:$Red ERROR - Install failed please contact admin system...$Reset" >&2
    exit 1
fi
echo -e "mvn install:$Green SUCCEED !$Reset"

echo -e "$Cyan
Installation finish with succeed.$Reset
To start development server run:
    ./bin/start.sh
"
