package com.tedcadet.donneeslaval.contrats.spark.transformers

import org.apache.spark.sql.DataFrame

trait DfTransformer extends Query {
  /** an higher order function qui prend de maniere implicit le contrat,
   * une query et retourne un DataFrame
   *
   * @param query type QueryNoParam. requete a utiliser pour transformer la DataFrame
   * @param df    DataFrame. Une table de colonnes et de rangees representant des donnees
   * @return un DataFrame transformé par la requête
   */
  def transformContratNoParam(query: QueryNoParam)(implicit df: DataFrame): DataFrame = query(df)

  /** an higher order function qui prend de manière implicit le contrat,
   * une query, un paramètre et retourne un DataFrame
   *
   * @param query type QueryNoParam. requête à utiliser pour transformer la DataFrame
   * @param param un paramètre nécessaire pour la requête. Exemple: un string représentant le nom de la colonne
   * @param df    un DataFrame. Une table de colonnes et de rangées représentant des donnees
   * @tparam A le type du paramètre à passer à la requête
   * @return un DataFrame transformé par la requête
   */
  def transformContratOneParam[A](query: QueryOneParam[A], param: A)(implicit df: DataFrame): DataFrame =
    query(df, param)

  // TODO: une methode qui map.get(id).union(map.get(id))?
  // transforme une sequence de queries? fold?
  //  def transformContratWithManyQueries[A](queries: Seq[QueryOneParam[A]], params: A*)(implicit df: DataFrame)
}
