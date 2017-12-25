package com.knoldus.spark.streaming.app

import java.util.Properties

import com.knoldus.spark.streaming.util.KafkaConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object ProducerApplication extends App {
  val jsonString = """{"person_id": "2", "first_name": "Anuj", "last_name": "Saxena", "age": 24}"""

  val topic = KafkaConfig.getValue("topic")
  val broker = KafkaConfig.getValue("broker")

  val properties = new Properties()
  properties.put("bootstrap.servers", broker)
  properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](properties)
  Range(1, 300).map { key =>
    val record = new ProducerRecord[String, String](topic, key.toString, jsonString)
    producer.send(record).get().toString
  }.exists(_.nonEmpty)

  producer.close()

}
