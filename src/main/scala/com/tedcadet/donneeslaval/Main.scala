package com.tedcadet.donneeslaval

import com.tedcadet.donneeslaval.contrats.ContratQueries.{listeContractantsQueries, montantCumulatifParContractantsQuery, montantDepenseParAnneeQuery}
import org.apache.spark.sql.{DataFrame, SparkSession}

object Main extends App {

  case class Contractant(contractant: String)
  // TODO: peut etre externaliser dans un fichier conf
  // creation du sparkSession
  val sparkSession: SparkSession = SparkSession.builder
    .appName("file-json-processing-test")
    .master("local[1]")
    .getOrCreate()

  import sparkSession.implicits._

  // TODO: le path est a externaliser dans un fichier conf
  // obtention des contrats a partir du ficher JSON
  val path: String = "src/main/ressources/contrats-octroyes.json"

  lazy val contrats: DataFrame = sparkSession
    .read
    .option("multiline", "true")
    .json(path)

//  contrats.printSchema()

  //liste des contractants
  val listeContractants: DataFrame = listeContractantsQueries(contrats)

  // montant cumulatif pour chacun des contractants
  val montantCumulatifParContractants: DataFrame = montantCumulatifParContractantsQuery(contrats)

  // argent depensee par annee
  val montantDepenseParAnnee: DataFrame = montantDepenseParAnneeQuery(contrats)

  // show les resultats
  listeContractants.show()
  montantCumulatifParContractants.show()
  montantDepenseParAnnee.show()
  
  val contractant = listeContractants.as[Contractant].first().toString

  // fermeture de la session
  sparkSession.close()
}
