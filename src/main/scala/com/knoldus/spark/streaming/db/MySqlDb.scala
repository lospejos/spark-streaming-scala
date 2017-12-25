package com.knoldus.spark.streaming.db

import java.util.Properties

import com.knoldus.spark.streaming.util.MySQLConfig
import org.apache.spark.sql.{DataFrame, SaveMode}

import scala.util.control.NonFatal

class MySqlDb extends GenericDB {

  val host = MySQLConfig.getValue("host")
  val database = MySQLConfig.getValue("database")
  val prefix = MySQLConfig.getValue("prefix")
  val port = MySQLConfig.getValue("port")

  val url = s"$prefix$host:$port/$database"
  val table = MySQLConfig.getValue("table")

  val user = MySQLConfig.getValue("user")
  val password = MySQLConfig.getValue("password")
  val driver = MySQLConfig.getValue("driver")

  val prop = new Properties()
  prop.setProperty("user", user)
  prop.setProperty("password", password)
  prop.put("driver", driver)

  def saveDataFrame(dataFrame: DataFrame): Boolean = {
    try {
      dataFrame.write.mode(SaveMode.Append).jdbc(url, table, prop)
      true
    } catch {
      case NonFatal(ex) => ex.printStackTrace()
        false
    }
  }

}
