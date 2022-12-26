ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

// add "addSbtPlugin("com.lightbend.akka.grpc" % "sbt-akka-grpc" % "2.2.1")" in plugins.sbt
enablePlugins(AkkaGrpcPlugin)

lazy val root = (project in file("."))
  .settings(
    name := "processing-contrats-laval-spark"
  )

lazy val SparkVersion = "3.3.1"
lazy val AkkaVersion = "2.8.0-M1"
lazy val AkkaHttpVersion = "10.5.0-M1"
lazy val GrpcVersion = "1.51.0"

libraryDependencies ++= Seq(
  // spark
  "org.apache.spark" %% "spark-core" % SparkVersion,
  "org.apache.spark" %% "spark-sql" % SparkVersion,
  // akka
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-core" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.lightbend.akka.grpc" %% "akka-grpc-runtime" % "2.3.0-M1",
  // grpc
  "com.thesamet.scalapb" %% "scalapb-runtime" % "0.11.12",
  "com.google.protobuf" % "protobuf-java" % "3.21.10",
  "io.grpc" % "grpc-core" % GrpcVersion,
  "io.grpc" % "grpc-netty-shaded" % GrpcVersion,
  "io.grpc" % "grpc-protobuf" % GrpcVersion,
)