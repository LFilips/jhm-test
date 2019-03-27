package com.filipponi.jmh

import java.util.concurrent.TimeUnit

import com.filipponi.jmh.ScalaState.BenchmarkState
import org.openjdk.jmh.annotations._

import scala.util.Try

/**
  * The idea behind this is to test the impact of throwing exception.
  * One of the classic use case of using exceptions for controlling the flow of the program is to use for example
  * the illegalArgumentException that is usually thrown when you use the "illegal argument" on the things that you are
  * using.
  * Scala has "error" datatype that can be used instead of throwing exceptions.
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ExceptionOrNotException {


  /*I have to wrap the exception in the try otherwise the jhm will fail*/
  @Benchmark
  def withException[A](state: BenchmarkState[A]) : Try[A]  = {
    Try(state.list.headOption match {
      case None => throw new IllegalArgumentException("The list is empty!")
      case Some(value) => value
    })
  }

  @Benchmark
  def withoutException[A](state: BenchmarkState[A]) : Either[InvalidInput,A]  = {
    state.list.headOption match {
      case None => Left(InvalidInput)
      case Some(value) => Right(value)
    }
  }
}

object ScalaState {
  @State(Scope.Benchmark)
  class BenchmarkState[A]{
    var list: List[A] = List()
  }
}

sealed trait InvalidInput
object InvalidInput extends InvalidInput


/**
  *
  * [info] Benchmark                                 Mode  Cnt     Score     Error  Units
  * [info] ExceptionOrNotException.withException     avgt    5  1153.889 ± 128.974  ns/op
  * [info] ExceptionOrNotException.withoutException  avgt    5     4.388 ±   0.459  ns/op
  *
  * Are this result possible? A single cpu in my machine is 2.2 GHz, i can have 2 000 000 000 operations per second,
  * this mean that i have 2 operation per nano seconds. Since the scoper is 4.891 means that the version without
  * exception is resolved in two cpu clock, this means that only two operation are performed:
  *
  *
  */

