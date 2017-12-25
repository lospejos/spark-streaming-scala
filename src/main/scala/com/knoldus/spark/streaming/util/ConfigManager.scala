package com.knoldus.spark.streaming.util

import com.typesafe.config.{Config, ConfigFactory}

class ConfigManager(configPath: Option[String]) {
  private val root_config = ConfigFactory.load()

  protected val config: Config = configPath match {
    case Some(path) => root_config.getConfig(path)
    case None => root_config
  }

  def getValue(string: String): String = config.getString(string)
}

object KafkaConfig extends ConfigManager(Some("knoldus.kafka"))
object MySQLConfig extends ConfigManager(Some("knoldus.mysql"))