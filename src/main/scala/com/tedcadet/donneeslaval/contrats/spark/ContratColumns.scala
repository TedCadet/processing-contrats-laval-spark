package com.tedcadet.donneeslaval.contrats.spark

import com.tedcadet.donneeslaval.contrats.spark.ContratUserDefinedFunctions.{toBigDecimal, toSubStrAnneeUdf}
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions.col

trait ContratColumns {
  
  // nom des colonnes
  val montantString: String = "montant"
  val contractantString: String = "contractant"
  val dateString: String = "date"
  val natureString: String = "nature"
  val noReferenceString: String = "no-reference"
  val anneString: String = "annee"

  // definition des colonnes
  val colMontant: Column = col(montantString)
  val colBigMontant: Column = toBigDecimal(col(montantString))
  val colContractant: Column = col(contractantString)
  val colDate: Column = col(dateString)
  val colNature: Column = col(natureString)
  val colNoReference: Column = col(noReferenceString)
  val colAnne: Column = toSubStrAnneeUdf(colDate)

  // map contenant les colonnes
  val columns: Map[String, Column] = Map(
    montantString -> colBigMontant,
    contractantString -> colContractant,
    dateString -> colDate,
    natureString -> colNature,
    noReferenceString -> colNoReference,
    anneString -> colAnne
  )
}
