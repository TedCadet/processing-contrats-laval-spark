package com.tedcadet.donneeslaval.contrats.spark.actors

import akka.NotUsed
import akka.actor.{Actor, Props}
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.stream.scaladsl.Source
import com.tedcadet.donneeslaval.contrats.spark.transformers.ContratsDfTransformer.montantCumulatifParQuery
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

// Trop complique pour rien, a moins qu'un actor puisse reply une Source plutot qu'un Future
object ContratSparkQueryProcessor {
  // TODO: le path est a externaliser dans un fichier conf
  protected val path: String = "src/main/ressources/contrats-octroyes.json"

  sealed trait Command

  case class GetListeContractants(replyTo: ActorRef[Reply]) extends Command

  case class GetMontantCumulatifParContractants(replyTo: ActorRef[Reply]) extends Command

  sealed trait Reply

  case class ListeContractantsReply(source: Source[Row, NotUsed]) extends Reply

  case class MontantCumulatifParContractantsReply(df: DataFrame) extends Reply

  def props() = Props(new ContratSparkQueryProcessor(path))
}

class ContratSparkQueryProcessor(path: String)
  extends Actor {

  import com.tedcadet.donneeslaval.contrats.spark.actors.ContratSparkQueryProcessor._

  // TODO: peut etre externaliser dans un fichier conf
  private val sparkSession: SparkSession = SparkSession.builder
    .appName("file-json-processing-test")
    .master("local[2]")
    .getOrCreate()

  private val contrats: DataFrame = sparkSession
    .read
    .option("multiline", "true")
    .json(path)
    .cache()

  def receive = ???
  //    case GetListeContractants(replyTo) => onGetListeContractants(replyTo)
  //    case GetMontantCumulatifParContractants(replyTo) => onGetMontantCumulatifParContractants(replyTo)


  //  private def onGetListeContractants(replyTo: ActorRef[Reply]): Behavior[Command] = {
  //    replyTo ! ListeContractantsReply(Source(listeContractantsQuery(contrats).collect()))
  //    Behaviors.same
  //  }

  //  private def onGetMontantCumulatifParContractants(replyTo: ActorRef[Reply]): Behavior[Command] = {
  //    replyTo ! MontantCumulatifParContractantsReply(montantCumulatifParContractantsQuery(contrats))
  //    Behaviors.same
  //  }
}
