package com.knoldus.spark.streaming.db

import org.apache.spark.sql.DataFrame

trait GenericDB {

  def saveDataFrame(dataFrame: DataFrame): Boolean

}
