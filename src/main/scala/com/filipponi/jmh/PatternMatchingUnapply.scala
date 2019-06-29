package com.filipponi.jmh
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
class PatternMatchingUnapply {

  /**
  *   This is test case that i want to try. I want to see if there is any speed improvement when using pattern matching
    *   with unapply or if just matching the class type. this are the two cases.
    *
    *   Option(something) match {
    *   case Some(value) => do stuff
    *   case None => do stuff
    *   }
    *
    *   or doing:
    *
    *   Option(something) match {
    *   case x: Some[_] => do stuff
    *   case y: None => do stuff
    *   }
    *
    *   Because i can imagine that in the first case hte unapply method is called and can be rather complex where in
    *   the second case is just a comparison with the class type. Obviously if i need to access a value from the
    *   destructured object this is not possible.
    *
    *   In case you don't need the inner of the class how much is the overhead of unapplying the object
    *
    */

  val some = Option(1)
  val none = Option(null)

  @Benchmark
  def patterMatchUnapplySome(): Option[Int] = {
    some match {
      case Some(value) => Some(value)
      case None => None
    }
  }

  @Benchmark
  def patterMatchNoUnapplySome(): Option[Int] = {
    some match {
      case a: Some[_] => a
      case b: None.type => b
    }
  }

  @Benchmark
  def patterMatchUnapplyNone(): Option[Int] = {
    some match {
      case Some(value) => Some(value)
      case None => None
    }
  }

  @Benchmark
  def patterMatchNoUnapplyNone(): Option[Int] = {
    some match {
      case a: Some[_] => a
      case b: None.type => b
    }
  }


}


/**
  * [info] Benchmark                                        Mode  Cnt  Score   Error  Units
  * [info] PatternMatchingUnapply.patterMatchNoUnapplyNone  avgt    5  2.370 ± 0.015  ns/op
  * [info] PatternMatchingUnapply.patterMatchNoUnapplySome  avgt    5  2.368 ± 0.025  ns/op
  * [info] PatternMatchingUnapply.patterMatchUnapplyNone    avgt    5  4.778 ± 0.385  ns/op
  * [info] PatternMatchingUnapply.patterMatchUnapplySome    avgt    5  5.352 ± 1.562  ns/op
  *
  * TODO I should dig into the generated java code to see what happen
  *
  */
