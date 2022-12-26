package com.tedcadet.donneeslaval.global.userdefinedfunctions

import org.apache.spark.sql.expressions.UserDefinedFunction

trait YearUserDefinedFunctions {
  // definition des UserDefinedFunction
  val toSubStrAnneeUdf: UserDefinedFunction
  def toSubStrAnnee(str: String): String
}
