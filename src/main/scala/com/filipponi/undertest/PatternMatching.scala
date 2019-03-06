package com.filipponi.undertest

import com.filipponi.undertest.PatternMatching.number
import org.openjdk.jmh.annotations.Benchmark

class PatternMatching {

  //@Benchmark
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

  //@Benchmark
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


/**
  * This are the result produced by this test with 1 fork, 1 thread, 20 iteration and 10 warmup:
  *
  * [info] Benchmark                        Mode  Cnt          Score        Error  Units
  * [info] PatternMatching.firstCondition  thrpt   20  337208045.990 ± 587223.165  ops/s
  * [info] PatternMatching.lastCondition   thrpt   20  338981764.563 ± 575461.111  ops/s
  *
  *
  * [info] Result "com.filipponi.undertest.PatternMatching.firstCondition":
  * [info]   334405591.515 ±(99.9%) 4353844.977 ops/s [Average]
  * [info]   (min, avg, max) = (320844818.390, 334405591.515, 338540917.996), stdev = 5013894.351
  * [info]   CI (99.9%): [330051746.538, 338759436.492] (assumes normal distribution)
  *
  *
  * The value of the firstCondition is slightly better, but a second run gave the opposite so there is possibly not
  * much differnce in something like that.
  *
  */