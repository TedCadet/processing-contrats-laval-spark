package com.tedcadet.donneeslaval.contrats.spark

import com.tedcadet.donneeslaval.contrats.spark.ContratsDfTransformer._
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.{DataFrame, SparkSession}
import pureconfig._
import pureconfig.generic.auto._


// TODO: use a class instead so that instance could keep the state of this read?
object ContratSparkService {
  final case class SparkConfig(appName: String, master: String, path: String)

  // TODO: apply(contrats, sparkSession)?

  val sparkConfig: SparkConfig = ConfigSource.default.at("spark")
    .load[SparkConfig]
    .getOrElse(
      SparkConfig("file-json-processing-test", "local[2]", "src/main/ressources/contrats-octroyes.json")
    )

  val sparkSession: SparkSession = SparkSession.builder
    .appName(sparkConfig.appName)
    .master(sparkConfig.master)
    .getOrCreate()

  val path: String = sparkConfig.path

  val contrats: DataFrame = sparkSession
    .read
    .option("multiline", "true")
    .json(path)
    .cache()

  // TODO: Map(id: String -> DataFrame)?
  // TODO: map.get(id).union(map.get(id))?
  //liste des contractants
  val listeContractants: DataFrame = listeContractantsQuery(contrats)

  // montant cumulatif pour chacun des contractants
  val montantCumulatifParContractants: DataFrame = montantCumulatifParContractantsQuery(contrats)

  // argent depensee par annee
  val montantDepenseParAnnee: DataFrame = montantDepenseParAnneeQuery(contrats)

  // liste des natures de contrats
  val listeDesNaturesDeContrat: DataFrame = listeNaturesDeContratQuery(contrats)

  // argent depensee par nature du contrat
  val montantOctroyeParNatures: DataFrame = montantOctroyeParNaturesQuery(contrats)

  // liste de contrat selon leur nature
  val listeContractantParNature: DataFrame = listeContractantParNatureQuery(contrats, "Acquisition")

  def closeSession(): Unit = sparkSession.close()
}
