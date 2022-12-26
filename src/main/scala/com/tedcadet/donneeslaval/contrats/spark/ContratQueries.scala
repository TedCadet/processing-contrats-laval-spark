package com.tedcadet.donneeslaval.contrats.spark

import com.tedcadet.donneeslaval.contrats.spark.Columns._
import com.tedcadet.donneeslaval.global.queries.DfQueryTypes
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Column, DataFrame, functions}

// TODO: re-use the function with ".compose" and/or ".andThen" to create other queries
private object ContratQueries extends DfQueryTypes {


  /**
   * description de la transformation d'un DataFrame qui retourne
   * une liste des elements distincts d'une colonne
    */
  private val listeParColonneDistinctQuery: QueryOneParam[Column] = {
    (dataFrame, column) =>
      dataFrame.select(column).distinct
  }

  /**
   * description de la transformation d'un DataFrame contenant les contrats
   * et qui retourne la liste des contractants
   */
  val listeContractantsQuery: QueryNoParam =
    contratsDF => listeParColonneDistinctQuery(contratsDF, colContractant)

  //TODO: passe comme parametre le type de contractant. Pour l'instant il recherche que les contractants en construction
  /**
   * description de la transformation d'un DataFrame contenant les contrats
   * et qui retourne les montants cumulatif depensee par contractant
   */
  val montantCumulatifParContractantsQuery: QueryNoParam = {
    contratsDF =>
      contratsDF
        .select(colContractant, colMontant)
        .groupBy(colContractant)
        .agg(functions.sum(colBigMontant).as("montant cumulatif(par consultant)"))
        .orderBy(col("montant cumulatif(par consultant)").desc)
  }

  /**
   * description de la transformation d'un DataFrame contenant les contrats
   * et qui retourne le montant depensee par annee
   */
  val montantDepenseParAnneeQuery: QueryNoParam  = {
    contratsDF =>
      contratsDF
        .select(colDate, colMontant)
        .groupBy(colAnne.as("annee"))
        .agg(functions.sum(colBigMontant).as("montant cumulatif(par annee)"))
        .orderBy(colAnne)
  }

  /**
   * description de la transformation d'un DataFrame contenant les contrats
   * et qui retourne la liste des natures des contrats
   */
  val listeNaturesDeContratQuery: QueryNoParam = {
    contratsDF =>
      contratsDF
        .select(colNature)
        .distinct()
  }

  /**
   * description de la transformation d'un DataFrame contenant les contrats
   * et qui retourne le montant depense par nature de contrats
   */
  val montantOctroyeParNaturesQuery: QueryNoParam  = {
    contratDF =>
      contratDF
        .select(colNature, colMontant)
        .groupBy(colNature.as("nature du contrat"))
        .agg(functions.sum(colBigMontant).as("montant cumulatif(par nature du contrat)"))
        .orderBy(col("montant cumulatif(par nature du contrat)").desc)
  }

  /**
   * description de la transformation d'un DataFrame contenant les contrats
   * et qui retourne la liste des contractants selon la nature du contrat
   */
  val listeContractantParNatureQuery: QueryOneParam[String] = {
    (contratsDF, nature) =>
      contratsDF
        .select("*")
        //TODO: Security Risk SQL injection: voir comment remplacer sans passer directement la nature
        .where(s" nature like '%$nature%'")
  }

}
