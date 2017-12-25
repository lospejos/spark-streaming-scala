package com.knoldus.spark.streaming.app

import com.knoldus.spark.streaming.db.MySqlDb
import com.knoldus.spark.streaming.service.StreamingService
import com.knoldus.spark.streaming.util.{KafkaConfig, StreamingOperations}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingApplication extends App {

  val spark = SparkSession.builder().master("local[2]").appName("streaming-app").getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")
  val ssc = new StreamingContext(spark.sparkContext, Seconds(1))
  val kafkaParams = Map(
    "bootstrap.servers" -> KafkaConfig.getValue("broker"),
    "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
    "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
    "group.id" -> KafkaConfig.getValue("group.id"),
    "auto.offset.reset" -> KafkaConfig.getValue("auto.offset.reset")
  )
  val topic = KafkaConfig.getValue("topic")
  val mySqlDb = new MySqlDb
  val streamingService = new StreamingService(mySqlDb)
  val streamingOperations = new StreamingOperations

  val dStream = streamingService.createStream(ssc, kafkaParams, topic)
  val filteredDStream = streamingOperations.distinct(dStream)
  streamingService.writeStream(filteredDStream).print
  println("Now starting streaming")
  ssc.start
  ssc.awaitTermination //OrTimeout(60000L)
  spark.close()
}
