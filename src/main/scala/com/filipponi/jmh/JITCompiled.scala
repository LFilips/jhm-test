package com.filipponi.jmh
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class JITCompiled {

  var size = 1000000000

  /**
  * With this example I hope to see the jit compilation to kick in and significantly increase the execution of this method.
    * Since the while instruction is executed tons for time is a good candidate for being jit compiled. If i can see
    * the jit compilation happening with the execution time that i'll be able to
    */
  @Benchmark
  def shouldJitCompile(): Int = {
    var i = 0
    while (i<size) {
      i+=1
    }
    i
  }

}
