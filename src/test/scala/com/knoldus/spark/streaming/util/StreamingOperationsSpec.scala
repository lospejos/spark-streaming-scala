package com.knoldus.spark.streaming.util

import com.holdenkarau.spark.testing.StreamingSuiteBase
import net.manub.embeddedkafka.EmbeddedKafka
import org.scalatest.WordSpec

class StreamingOperationsSpec extends WordSpec with StreamingSuiteBase with EmbeddedKafka {

  val streamingOperations = new StreamingOperations

  "StreamingOperations" should {

    "remove duplicates" in {
      val inputPair = List(List((1, "the word the"), (1, "the word the")))
      val pair = List(List((1, "the word the")))

      testOperation(inputPair, streamingOperations.distinct _, pair, ordered = false)
    }

    "update stream" in {
      val inputPair = List(List((1, """{"name": "anuj"}, {"name": "raman"}""")))
      val pair = List(List((1, """{"value":[{"name": "anuj"}, {"name": "raman"}]}""")))

      testOperation(inputPair, streamingOperations.update _, pair, ordered = false)
    }
  }
}