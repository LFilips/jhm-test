package com.filipponi.undertest

import com.filipponi.undertest.MapAndView._

import scala.util.Random


class MapAndView {

  /**
    * This simple test showed that the best thing to do is to compose function if possible,
    * and if not possible is better to use views to lazily apply the transformation instead of using many
    * maps.
    *
    */

//  @Benchmark
  def consecutiveMap(): List[Int] = {
    testList.map(map1).map(map2).map(map3).map(map4).map(map5)
  }

//  @Benchmark
  def consecutiveMapUsingView(): List[Int] = {
    testList.view.map(map1).map(map2).map(map3).map(map4).map(map5).toList
  }

//  @Benchmark
  def composingFunction(): List[Int] = {
    testList.map(map1.compose(map2).compose(map3).compose(map4).compose(map5))
  }

}

// FIXME i'm not sure about which is the best way to pass value
object MapAndView{
  val rand: Random.type = Random
  val testList: List[Int] = List.fill(1000)(rand.nextInt())
  val map1: Int => Int = (x:Int) => x*2
  val map2: Int => Int = (x:Int) => x*3
  val map3: Int => Int = (x:Int) => x*4
  val map4: Int => Int = (x:Int) => x*5
  val map5: Int => Int = (x:Int) => x*6
}
