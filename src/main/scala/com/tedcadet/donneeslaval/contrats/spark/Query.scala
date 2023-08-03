package com.tedcadet.donneeslaval.contrats.spark

trait Query {
  type QueryNoParam
  type QueryOneParam[A]
}
