package com.knoldus.spark.streaming.util

import org.apache.spark.streaming.{State, StateSpec}
import org.apache.spark.streaming.dstream.DStream

class StreamingOperations {

  def update(dStream: DStream[(Int, String)]): DStream[(Int, String)] = {
    dStream.map{
      case (key, value) => (key, s"""{"value":[$value]}""")
    }
  }

  def distinct(dStream: DStream[(Int, String)]): DStream[(Int, String)] = {
    dStream.mapWithState(StateSpec.function(dedup))
      .flatMap {
        case Some(value) => Seq(value)
        case _ => Seq()
      }
  }

  val dedup = (key: Int, value: Option[String], state: State[List[Int]]) => {
    (value, state.getOption()) match {
      case (Some(data), Some(keys)) if !keys.contains(key) =>
        state.update(key :: keys)
        Some(key, data)
      case (Some(data), None) =>
        state.update(List(key))
        Some(key, data)
      case _ =>
        None
    }
  }

}
