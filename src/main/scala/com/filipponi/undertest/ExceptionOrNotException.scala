package com.filipponi.undertest

import java.util.concurrent.TimeUnit

import com.filipponi.undertest.ScalaState.BenchmarkState
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
  * First preliminary test shows that the withoutException version is much faster.
  * [info] Benchmark                                  Mode  Cnt          Score         Error  Units
  * [info] ExceptionOrNotException.withException     thrpt   25     412023.978 ±   32711.067  ops/s
  * [info] ExceptionOrNotException.withoutException  thrpt   25  120181590.392 ± 6120455.705  ops/s
  */

