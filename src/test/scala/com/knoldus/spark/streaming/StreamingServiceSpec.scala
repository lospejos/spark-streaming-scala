package com.knoldus.spark.streaming

import com.knoldus.spark.streaming.db.MySqlDb
import com.knoldus.spark.streaming.service.StreamingService
import com.knoldus.spark.streaming.util.{KafkaConfig, StreamingOperations}
import net.manub.embeddedkafka.{EmbeddedKafka, EmbeddedKafkaConfig}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, WordSpec}

class StreamingServiceSpec extends WordSpec with BeforeAndAfterAll with EmbeddedKafka with MockitoSugar {
  lazy val spark: SparkSession = SparkSession
    .builder
    .master("local[*]")
    .appName("testing")
    .getOrCreate
  val mockedDB = mock[MySqlDb]
  val mockedOperations = mock[StreamingOperations]
  val streamingService = new StreamingService(mockedDB)

  implicit val config: EmbeddedKafkaConfig = EmbeddedKafkaConfig(kafkaPort = 9092, zooKeeperPort = 2181)
  val kafkaParams = Map(
    "bootstrap.servers" -> KafkaConfig.getValue("broker"),
    "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
    "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
    "group.id" -> KafkaConfig.getValue("group.id"),
    "auto.offset.reset" -> KafkaConfig.getValue("auto.offset.reset")
  )
  val topic = KafkaConfig.getValue("topic")
  var ssc: StreamingContext = _

  override protected def beforeAll(): Unit = {
    EmbeddedKafka.start()(config)
    ssc = new StreamingContext(spark.sparkContext, Seconds(1))
  }

  "StreamingService" should {

    "create DStream" in {
      val dStream = streamingService.createStream(ssc, kafkaParams, topic)
      assert(dStream.slideDuration == Seconds(1))
    }
  }

  override protected def afterAll(): Unit = {
    EmbeddedKafka.stop()
  }
}