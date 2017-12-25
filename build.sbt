name := "spark-streaming-scala"

version := "0.1"

scalaVersion := "2.11.8"

val spark2Ver = "2.1.0"
val spark2JacksonVer = "2.8.7"
val embeddedKafkaVersion = "0.15.1"

val spark2Core = "org.apache.spark" %% "spark-core" % spark2Ver
val spark2Sql = "org.apache.spark" %% "spark-sql" % spark2Ver
val spark2hive =  "org.apache.spark" %% "spark-hive" % spark2Ver
val spark2SqlKafka =  "org.apache.spark" %% "spark-sql-kafka-0-10" % spark2Ver
val spark2Streaming = "org.apache.spark" %% "spark-streaming" % spark2Ver
val kafkaClients = "org.apache.kafka" % "kafka-clients" % "0.10.0.0"
val spark2StreamingKafka10 = "org.apache.spark" %% "spark-streaming-kafka-0-10" % spark2Ver
val typesafeConfig = "com.typesafe" % "config" % "1.3.2"
val mysqlJava = "mysql" % "mysql-connector-java" % "5.1.6"
//testing
val embeddedKafka = "net.manub" %% "scalatest-embedded-kafka" % embeddedKafkaVersion % Test
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % "test"
val sparkTestingBase = "com.holdenkarau" %% "spark-testing-base" % "2.1.0_0.8.0" % Test
val mockitoCore = "org.mockito"  % "mockito-core" % "1.9.5" % "test"

libraryDependencies ++= Seq(
  spark2Core,
  spark2Streaming,
  spark2Sql,
  kafkaClients,
  spark2StreamingKafka10,
  spark2hive,
  mysqlJava,
  typesafeConfig,
  //testing
  sparkTestingBase,
  embeddedKafka,
  scalaTest,
  mockitoCore
)
