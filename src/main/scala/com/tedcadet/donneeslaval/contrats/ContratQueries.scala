package com.tedcadet.donneeslaval.contrats

import com.tedcadet.donneeslaval.contrats.Columns.{colAnne, colBigMontant, colContractant, colDate, colMontant, colNature}
import com.tedcadet.donneeslaval.contrats.ContratUserDefinedFunctions.toBigDecimal
import org.apache.spark.sql.{Column, DataFrame, functions}
import org.apache.spark.sql.functions.col

object ContratQueries {

  /**
   *
   * @param contratsDF liste des contrats dans un dataFrame
   * @param colonne colonne qu'on veut recuperer
   * @return liste des elements distincts d'une colonne
   */
  def listeParColonneDistinctQuery(contratsDF: DataFrame, colonne: Column): DataFrame =
    contratsDF.select(colonne).distinct

  /**
   * @param contratsDF liste des contrats dans un dataFrame
   * @return la liste des contractants
   */
  def listeContractantsQuery(contratsDF: DataFrame): DataFrame =
    contratsDF.select(colContractant).distinct

  //TODO: passe comme parametre le type de contractant. Pour l'instant il recherche que les contractants en construction
  /**
   * @param contratsDF liste des contrats dans un dataFrame
   * @return le montant cumulatif par contractants
   */
  def montantCumulatifParContractantsQuery(contratsDF: DataFrame): DataFrame =
    contratsDF
      .select(colContractant, colMontant)
      .groupBy(colContractant)
      .agg(functions.sum(colBigMontant).as("montant cumulatif(par consultant)"))
      .orderBy(col("montant cumulatif(par consultant)").desc)

  /**
   *
   * @param contratsDF liste des contrats dans un dataFrame
   * @return le montant depense par annee
   */
  def montantDepenseParAnneeQuery(contratsDF: DataFrame): DataFrame =
    contratsDF
      .select(colDate, colMontant)
      .groupBy(colAnne.as("annee"))
      .agg(functions.sum(colBigMontant).as("montant cumulatif(par annee)"))
      .orderBy(colAnne)

  /**
   *
   * @param contratsDF liste des contrats dans un dataFrame
   * @return la liste des natures de contrat
   */
  def listeNaturesDeContratQuery(contratsDF: DataFrame): DataFrame =
    contratsDF
      .select(colNature)
      .distinct()

  /**
   *
   * @param contratDF liste des contrats dans un dataFrame
   * @return le montant depense par nature de contrats
   */
  def montantOctroyeParNaturesQuery(contratDF: DataFrame): DataFrame =
    contratDF
      .select(colNature, colMontant)
      .groupBy(colNature.as("nature du contrat"))
      .agg(functions.sum(colBigMontant).as("montant cumulatif(par nature du contrat)"))
      .orderBy(col("montant cumulatif(par nature du contrat)").desc)

  def listeContractantParNatureQuery(contratsDF: DataFrame, nature: String): DataFrame =
    contratsDF
      .select("*")
      //TODO: Security Risk SQL injection: voir comment remplacer sans passer directement la nature
      .where(s" nature like '%${nature}%'")

}
