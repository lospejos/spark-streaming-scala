package com.knoldus.spark.streaming.service

import com.knoldus.spark.streaming.db.GenericDB
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

class StreamingService(genericDB: GenericDB) {

  def createStream(ssc: StreamingContext, kafkaParams: Map[String, String], topic: String): DStream[(Int, String)] = {
    KafkaUtils.createDirectStream(ssc, LocationStrategies.PreferConsistent, ConsumerStrategies.Subscribe[String, String](Iterable(topic), kafkaParams))
      .map(consumerRecord => consumerRecord.key().toInt -> consumerRecord.value())
  }

  def writeStream(dStream: DStream[(Int, String)]): DStream[(Int, String)] = {
    dStream.foreachRDD { rdd =>
      val spark = SparkSession.builder.config(rdd.sparkContext.getConf).getOrCreate()
      val df = spark.read.json(rdd.values)
      genericDB.saveDataFrame(df)
    }
    dStream
  }
}
