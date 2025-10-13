#!/bin/bash -e

grep "var protoVersion = " build.gradle.kts | grep -Eo "[0-9]+\.[0-9]+\.[0-9]+"
