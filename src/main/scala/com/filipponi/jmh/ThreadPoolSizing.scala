package com.filipponi.jmh
import java.util.concurrent.{Executors, TimeUnit}

import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit}
import org.openjdk.jmh.infra.Blackhole

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ThreadPoolSizing {

  /**
  * This method will use a number of thread equals to the cores (using scala execution context)
    */
  @Benchmark
  def threadAsCores(bh: Blackhole)  = {

    import scala.concurrent.ExecutionContext.Implicits.global

    val futures = List.fill(1000) {
     Future {
       cpuBoundTask()
     }
    }
    futures.foreach(Await.result(_,100 seconds))
    bh.consume(futures)
  }

//  @Benchmark
//  def tooManyThreads(bh: Blackhole)  = {
//
//    implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(100))
//
//    val futures = List.fill(1000) {
//      Future {
//        cpuBoundTask()
//      }
//    }
//    futures.foreach(Await.result(_,100 seconds))
//    bh.consume(futures)
//  }


  private def cpuTask(): Unit = {

  }

  /**
    * Trying to do something that is CPU bound
    *
    * @return a double calculated using some operations
    */
  def cpuBoundTask() : Double =  {
    List
      .tabulate(10000)(n => {
        val power = Math.pow(n, 7)
        Math.atan(power) + Math.tan(power)
      })
      .sum
  }

  /**
    * Using a sleep should be enough to simulate and IO Bound operation.
    */
  def ioBoundTask(): Double ={
    Thread.sleep(100)
    42.0
  }

}

