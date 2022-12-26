package com.tedcadet.donneeslaval.contrats.spark

import com.tedcadet.donneeslaval.contrats.spark.ContratQueries._
import org.apache.spark.sql.{DataFrame, SparkSession}

object ContratSparkService {
  // TODO: peut etre externaliser dans un fichier conf
  // creation du sparkSession
  val sparkSession: SparkSession = SparkSession.builder
    .appName("file-json-processing-test")
    .master("local[2]")
    .getOrCreate()

  // TODO: le path est a externaliser dans un fichier conf
  // obtention des contrats a partir du ficher JSON
  val path: String = "src/main/ressources/contrats-octroyes.json"

  lazy val contrats: DataFrame = sparkSession
    .read
    .option("multiline", "true")
    .json(path)
    .cache()

  //  contrats.printSchema()

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

//  def closeSession(): Unit = sparkSession.close()
}
