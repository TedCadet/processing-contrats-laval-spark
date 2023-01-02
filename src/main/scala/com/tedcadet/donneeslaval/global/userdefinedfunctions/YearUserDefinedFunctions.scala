package com.tedcadet.donneeslaval.global.userdefinedfunctions

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object YearUserDefinedFunctions {
  // definition des UserDefinedFunction
  val toSubStrAnneeUdf: UserDefinedFunction = udf((str: String) => toSubStrAnnee(str))

  val toSubStrAnnee: String => String = str =>
    Option(str).map(_.substring(0, 4)).getOrElse("null")
}
