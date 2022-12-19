package com.tedcadet.donneeslaval.globaluserdefinedfunctions

import org.apache.spark.sql.expressions.UserDefinedFunction

trait YearUserDefinedFunctions {
  // definition des UserDefinedFunction
  val toSubStrAnneeUdf: UserDefinedFunction
  def toSubStrAnnee(str: String): String
}
