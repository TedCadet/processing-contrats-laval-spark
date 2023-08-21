package com.tedcadet.donneeslaval.contrats.spark.transformers

import org.apache.spark.sql.DataFrame

trait Query {
  type QueryNoParam = DataFrame => DataFrame
  type QueryOneParam[A] = (DataFrame, A) => DataFrame
  
  // TODO: implements map, flatmap?
}
