package com.tedcadet.donneeslaval

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

trait YearUserDefinedFunctions {
  // definition des UserDefinedFunction
  val toSubStrAnneeUdf: UserDefinedFunction
  def toSubStrAnnee(str: String): String
}
