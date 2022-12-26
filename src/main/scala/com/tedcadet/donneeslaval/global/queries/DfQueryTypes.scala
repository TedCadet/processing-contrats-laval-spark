package com.tedcadet.donneeslaval.global.queries

import org.apache.spark.sql.{Column, DataFrame}

trait DfQueryTypes {
  type QueryNoParam = DataFrame => DataFrame
  type QueryOneParam[A] = (DataFrame, A) => DataFrame
}
