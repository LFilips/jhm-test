package com.filipponi.jmh

import java.util.concurrent.TimeUnit

import com.filipponi.jmh.MinAlgorithmState.ListState
import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import scala.collection.immutable.TreeSet
import scala.util.Random

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class CalculateMin {

  @Benchmark
  def listMin(state: ListState,hole: Blackhole): Unit = {
    val min = state.list.min
    hole.consume(min)
  }

  @Benchmark
  def treeSetMin(state: ListState, hole: Blackhole): Unit = {
    val min = state.treeSet.min
    hole.consume(min)
  }

  @Benchmark
  def treeSetMinWithFirstKey(state: ListState, hole: Blackhole): Unit = {
    val min = state.treeSet.firstKey
    hole.consume(min)
  }
}


object MinAlgorithmState {
  @State(Scope.Benchmark)
  class ListState{
    val rand = new scala.util.Random(0)
    var list: List[Int] = List.fill(100000)(rand.nextInt())
    val treeSet: TreeSet[Int] = TreeSet(list:_*)
  }
}
