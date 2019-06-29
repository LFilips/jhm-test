package com.filipponi.jmh

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import org.openjdk.jmh.infra.Blackhole

import scala.util.Random

/**
  * After this test i've seen that symbols interning has nothing to do with string interning. Symbols lives in another
  * cache that is handled by the constructor of the Symbol. In this way is guaranteed to be interned and the equality
  * check will pass before checking every char. The usage of this symbols is really limited and if you are operating
  * with string is really not worth it to use, since you'll need to lift a string into a symbol and this alredy remove
  * any possible advantage.
  */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread) //better understands what this implies
class SymbolOrStrings {

  private val knownWords = Seq("h"+"i", "th"+"is", "i"+"s", "a"+"n", "am"+"az"+"ing", "t"+"es"+"t", "t"+"o", "d"+"o", "a"+"n"+"d", "e"+"ve"+"rt"+"h"+"in"+"g", "wi"+"l"+"l", "b"+"e", "fa"+"s"+"ter")
  private val unknownWords = Seq("bad", "words")
  private val words = knownWords ++: unknownWords

  /**
    * This will create a big list randomly repeating those words. I'm keeping the reation between valid and invalid
    * to have much more valid words.
    */
  private val expandedRandom = (1 to 100).foldLeft(Seq.empty[String])((acc, _) => {
    acc ++: Random.shuffle(words)
  })

  private val expandedRandomSymbol = expandedRandom.map(Symbol(_))


  @Benchmark
  def stringPatternMatch(blackhole: Blackhole): Unit = {
    expandedRandom.foreach {
      case "hi" => blackhole.consume()
      case "this" => blackhole.consume()
      case "is" => blackhole.consume()
      case "an" => blackhole.consume()
      case "amazing" => blackhole.consume()
      case "test" => blackhole.consume()
      case "to" => blackhole.consume()
      case "do" => blackhole.consume()
      case "and" => blackhole.consume()
      case "everything" => blackhole.consume()
      case "will" => blackhole.consume()
      case "be" => blackhole.consume()
      case "faster" => blackhole.consume()
      case _ => blackhole.consume()
    }

  }

  @Benchmark
  def symbolPatternMatch(blackhole: Blackhole): Unit = {
    expandedRandomSymbol.foreach {
      case 'hi => blackhole.consume()
      case 'this => blackhole.consume()
      case 'is => blackhole.consume()
      case 'an => blackhole.consume()
      case 'amazing => blackhole.consume()
      case 'test => blackhole.consume()
      case 'to => blackhole.consume()
      case 'do => blackhole.consume()
      case 'and => blackhole.consume()
      case 'everything => blackhole.consume()
      case 'will => blackhole.consume()
      case 'be => blackhole.consume()
      case 'faster => blackhole.consume()
      case _ => blackhole.consume()
    }
  }

}