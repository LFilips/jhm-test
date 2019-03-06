
# Scala-performance

Simple project for the performance of different methods and code in
scala/java, and to understand how to use micro benchmarking to test jvm
code. It uses the sbt-jmh plugin.

All the test are kept in the folder but commented, because otherwise all
of the will be run and every test take time.

## How to Run

First:

`sbt clean compile`

Then run benchmark using 

`sbt jmh:run -i 20 -wi 10 -f1 -t1`

-i 20 says that we want to run each benchmark with 20 iterations 
-wi 10 says to run 10 warmup iterations 
-f 1 says to fork once on each benchmark 
-t1 says to run on one thread. 
