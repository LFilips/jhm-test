package com.filipponi.undertest

import com.filipponi.undertest.ScalaState.BenchmarkState
import org.openjdk.jmh.annotations.{Benchmark, Scope, State}

/**
  * The idea behind this is to test the impact of throwing exception.
  * One of the class use case of using exceptions for controlling the flow of the program is to use for example
  * the illegalArgumentException that is usually thrown when you use the "illegal argument" on the things that you are
  * using.
  *
  */
class ExceptionOrNotException {

  @Benchmark
  def withException[A](state: BenchmarkState[A]) : A  = {
    state.list.headOption match {
      case None => throw new IllegalArgumentException("The list is empty!")
      case Some(value) => value
    }
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

