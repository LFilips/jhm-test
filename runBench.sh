#!/usr/bin/env bash
sbt clean compile "jmh:run -i 10 -wi 10 -f1 -t1 .*$1.*"