package com.tedcadet.donneeslaval.contrats.spark.transformers

import com.tedcadet.donneeslaval.contrats.spark.traits.ContratColumns
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, functions}

// TODO: re-use the function with ".compose" and/or ".andThen" to create other queries
// add a queryBuilder?
// for-comprehension to compose the different generic queries?
object ContratsDfTransformer extends DfTransformer with ContratColumns with Query {

  private val montantCumulatifTxtTemplate: String => String =
    str => s"montant cumulatif par $str"

  /**
   * description de la transformation d'un DataFrame qui retourne
   * une liste des elements distincts d'une colonne
   */
  val listeParColonneDistinctQuery: QueryOneParam[String] = {
    (dataFrame, str) =>
      dataFrame.select(columns(str)).distinct
  }

  //TODO: passe comme parametre le type de contractant. Pour l'instant il recherche que les contractants en construction
  /**
   * description de la transformation d'un DataFrame contenant les contrats
   * et qui retourne les montants cumulatif depensee par contractant
   */
  val montantCumulatifParQuery: QueryOneParam[String] = {
    (contratsDF, nomColonne) =>
      contratsDF
        .select(columns(nomColonne), colMontant)
        .groupBy(columns(nomColonne))
        .agg(functions.sum(colBigMontant).as(montantCumulatifTxtTemplate(nomColonne)))
        .orderBy(col(montantCumulatifTxtTemplate(nomColonne)).desc)
  }

  /**
   * description de la transformation d'un DataFrame contenant les contrats
   * et qui retourne le montant depensee par annee
   */
  val montantDepenseParAnneeQuery: QueryNoParam = {
    contratsDF =>
      contratsDF
        .select(colDate, colMontant)
        .groupBy(colAnne.as("annee"))
        .agg(functions.sum(colBigMontant).as(montantCumulatifTxtTemplate("annee")))
        .orderBy(colAnne)
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
