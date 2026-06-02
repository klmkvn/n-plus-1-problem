#!/bin/sh
# Maven Wrapper launcher script
# -----------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.
# -----------------------------------------------------------------------
set -e

SCRIPT_DIR=$(dirname "$(readlink -f "$0" 2>/dev/null || realpath "$0" 2>/dev/null || echo "$0")")
JAR="$SCRIPT_DIR/.mvn/wrapper/maven-wrapper.jar"
PROPS="$SCRIPT_DIR/.mvn/wrapper/maven-wrapper.properties"

# Download wrapper jar if missing
if [ ! -f "$JAR" ]; then
  WRAPPER_URL=$(grep "wrapperUrl" "$PROPS" | cut -d= -f2-)
  echo "Downloading Maven Wrapper from: $WRAPPER_URL"
  curl -fsSL -o "$JAR" "$WRAPPER_URL"
fi

exec java -jar "$JAR" "$@"
