package com.tedcadet.donneeslaval

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

trait GlobalUserDefinedFunctions {
  // definition des UserDefinedFunction
  val toBigDecimal: UserDefinedFunction
  val toSubStrAnneeUdf: UserDefinedFunction

  def toSubStrAnnee(str: String): String
}
