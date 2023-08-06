package com.tedcadet.donneeslaval.contrats.spark.traits

trait Query {
  type QueryNoParam
  type QueryOneParam[A]
}
