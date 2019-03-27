package com.filipponi.jmh

import com.filipponi.jmh.PatternMatching.number
import org.openjdk.jmh.annotations.Benchmark

/**
  * This test was a bit inconclusive, since different runs are generating different result. It is fair to
  * assume that there is not real difference to have the right match as first or last condition, or at least this will
  * not influence enough the running time. Or it is possible that since i'm calling always with the same number the
  * result is somehow cached/memoized? I need to investigate a bit more around this
  */
class PatternMatching {

  @Benchmark
  def lastCondition(): Int = {
    number match {
      case 1 => 10
      case 2 => 8
      case 3 => 7
      case 4 => 6
      case 5 => 5
      case 6 => 4
      case 7 => 3
      case 8 => 2
      case 9 => 1
      case 10 => 0
      case _ => 41
    }
  }

  @Benchmark
  def firstCondition(): Int = {
    number match {
      case 10 => 0
      case 1 => 10
      case 2 => 8
      case 3 => 7
      case 4 => 6
      case 5 => 5
      case 6 => 4
      case 7 => 3
      case 8 => 2
      case 9 => 1
      case _ => 41
    }
  }
}

object PatternMatching {
  val number = 10
}
