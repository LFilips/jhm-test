package com.filipponi.jmh
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import com.filipponi.jmh.ListAlgorithmState.ListState

import scala.annotation.tailrec

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ListAlgorithm {

  @Benchmark
  def OnAlgorithm(state: ListState): List[Long] = {
    state.list.map(_ * 2)
  }

  @Benchmark
  def OnSquareAlgorithm(state: ListState): List[List[Long]] = {
    state.list.map(num => state.list.map(_ * 2))
  }

  @Benchmark
  def Olog2Algorithm(state: ListState): List[Long] = {

    @tailrec
    def Olog2AlgorithmRec(index: Int,
                          origin: List[Long],
                          acc: List[Long]) : List[Long] = {
      if (index == 0) {
        acc
      } else {
        Olog2AlgorithmRec(index / 2, origin, origin(index) :: acc )
      }
    }

    Olog2AlgorithmRec((state.list.size-1)/2,state.list,List.empty)
  }

  @Benchmark
  def OnSquareAlgorithmReturn(state: ListState): Unit = {
    state.list.map(num => state.list.map(_ * 2))
  }


}


object ListAlgorithmState {
  @State(Scope.Benchmark)
  class ListState{
    var list: List[Long] = List.fill(10000)(Math.random().toLong)
  }
}

/** sbt clean compile && sbt "jmh:run -i 1 -wi 1 -f1 -t1 .*ListAlgorithm*" */

/**
* [info] REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
  * [info] why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
  * [info] experiments, perform baseline and negative tests that provide experimental control, make sure
  * [info] the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
  * [info] Do not assume the numbers tell you what you want them to tell.
  *
  * [info] Benchmark                              Mode  Cnt           Score   Error  Units
  * [info] ListAlgorithm.Olog2AlgorithmReturn     avgt    2       35212.142          ns/op
  * [info] ListAlgorithm.OnAlgorithmReturn        avgt    2       41201.329          ns/op
  * [info] ListAlgorithm.OnSquareAlgorithmReturn  avgt    2  2929583630.875          ns/op
  */


/**
* [info] Benchmark                              Mode  Cnt           Score           Error  Units
  * [info] ListAlgorithm.Olog2AlgorithmReturn     avgt    5       35018.376 ±      1423.822  ns/op
  * [info] ListAlgorithm.OnAlgorithmReturn        avgt    5       41175.147 ±       699.645  ns/op
  * [info] ListAlgorithm.OnSquareAlgorithmReturn  avgt    5  2851475046.850 ± 804118545.395  ns/op
  * [success] Total time: 326 s, completed 26-Mar-2019 13:47:32
  */
