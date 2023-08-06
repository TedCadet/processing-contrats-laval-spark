package com.tedcadet.donneeslaval.contrats.spark.services

import com.tedcadet.donneeslaval.contrats.spark.transformers.ContratsDfTransformer._
import com.tedcadet.donneeslaval.contrats.spark.traits.ContratColumns
import org.apache.spark.sql.{DataFrame, SparkSession}
import pureconfig._
import pureconfig.generic.auto._


// TODO: use a class instead so that instance could keep the state of this read?
object ContratSparkService extends ContratColumns {
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

  implicit val contrats: DataFrame = sparkSession
    .read
    .option("multiline", "true")
    .json(path)
    .cache()

  //liste des contractants
  val listeContractants: DataFrame = transformContratOneParam(listeParColonneDistinctQuery, contractantString)

  // montant cumulatif pour chacun des contractants
  val montantCumulatifParContractants: DataFrame =
    montantCumulatifParQuery(contrats, contractantString)

  // argent depensee par annee
  val montantDepenseParAnnee: DataFrame = transformContratNoParam(montantDepenseParAnneeQuery)

  // liste des natures de contrats
  val listeDesNaturesDeContrat: DataFrame = transformContratOneParam(listeParColonneDistinctQuery, natureString)

  // argent depensee par nature du contrat
  val montantOctroyeParNatures: DataFrame = transformContratOneParam(montantCumulatifParQuery, natureString)

  // liste de contrat selon leur nature
  val listeContractantParNature: DataFrame = transformContratOneParam(listeContractantParNatureQuery, "Acquisition")

  def closeSession(): Unit = sparkSession.close()
}
