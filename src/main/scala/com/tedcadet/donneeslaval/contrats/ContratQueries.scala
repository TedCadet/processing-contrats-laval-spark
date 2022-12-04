package com.tedcadet.donneeslaval.contrats

import com.tedcadet.donneeslaval.contrats.Columns.{colAnne, colContractant, colDate, colMontant}
import com.tedcadet.donneeslaval.contrats.ContratUserDefinedFunctions.toBigDecimal
import org.apache.spark.sql.{DataFrame, functions}
import org.apache.spark.sql.functions.col

object ContratQueries {

  /**
   * @param contratsDF liste des contrats dans un dataFrame
   * @return la liste des contractants
   */
  def listeContractantsQueries(contratsDF: DataFrame): DataFrame =
    contratsDF.select(colContractant).distinct

  /**
   * @param contratsDF liste des contrats dans un dataFrame
   * @return le montant cumulatif par contractants
   */
  def montantCumulatifParContractantsQuery(contratsDF: DataFrame): DataFrame =
    contratsDF
      .select(colContractant, colMontant)
      //TODO: voir comment remplacer l'expression en utilisant directement la colonne
      .where(" nature like '%construction%'")
      .groupBy(colContractant)
      .agg(functions.sum(colMontant).as("montant cumulatif(par consultant)"))
      .orderBy(toBigDecimal(col("montant cumulatif(par consultant)")).desc)

  /**
   *
   * @param contratsDF liste des contrats dans un dataFrame
   * @return le montant depense par annee
   */
  def montantDepenseParAnneeQuery(contratsDF: DataFrame): DataFrame =
    contratsDF
      .select(colDate, colMontant)
      .groupBy(colAnne)
      .agg(functions.sum(colMontant).as("montant cumulatif(par annee)"))
      .orderBy(colAnne)
}
