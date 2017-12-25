package com.knoldus.spark.streaming.db

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.scalatest.WordSpec
//requires MySQL to be running
class MySqlDbSpec extends WordSpec {

  val mySqlDb = new MySqlDb

  val spark = SparkSession
    .builder()
    .master("local[2]")
    .appName("test")
    .getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")

  "MySqlDb" should {
    "saveDataFrame to database" in {
      pending
      val json = """{ "person_id":1, "first_name":"ANUJ", "last_name":"SAXENA", "age":"25" }"""
      val df: DataFrame = spark.read.json(spark.sparkContext.parallelize(Seq(json)))
      val result = mySqlDb.saveDataFrame(df)
      assert(result)
    }
  }

}
