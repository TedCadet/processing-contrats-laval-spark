ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"


// this specifies which class is the main class in the package
Compile / mainClass := Some("com.tedcadet.donneeslaval.GrpcServer")

// this will add the ability to "stage" which is required for Heroku
enablePlugins(JavaAppPackaging)

// add "addSbtPlugin("com.lightbend.akka.grpc" % "sbt-akka-grpc" % "2.2.1")" in plugins.sbt
enablePlugins(AkkaGrpcPlugin)

val SparkVersion = "3.4.1"
val AkkaVersion = "2.8.4"
val AkkaHttpVersion = "10.5.2"
val GrpcVersion = "1.57.2"

lazy val root = (project in file("."))
  .settings(
    name := "processing-contrats-laval-spark",
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
      "com.lightbend.akka.grpc" %% "akka-grpc-runtime" % "2.3.3",
      // grpc
      "com.thesamet.scalapb" %% "scalapb-runtime" % "0.11.13",
      "com.google.protobuf" % "protobuf-java" % "3.22.2",
      "io.grpc" % "grpc-core" % GrpcVersion,
      "io.grpc" % "grpc-netty-shaded" % GrpcVersion,
      "io.grpc" % "grpc-protobuf" % GrpcVersion,
      // config loader
      "com.github.pureconfig" %% "pureconfig" % "0.17.4",
    )
  )


