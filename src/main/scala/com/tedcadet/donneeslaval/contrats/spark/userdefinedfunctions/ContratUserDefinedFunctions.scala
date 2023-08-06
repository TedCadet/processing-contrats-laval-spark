package com.tedcadet.donneeslaval.contrats.spark.userdefinedfunctions

import com.tedcadet.donneeslaval.global.userdefinedfunctions.{NumbersUserDefinedFunctions, YearUserDefinedFunctions}
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object ContratUserDefinedFunctions extends YearUserDefinedFunctions with NumbersUserDefinedFunctions {
  override val toSubStrAnneeUdf: UserDefinedFunction = udf((str: String) => toSubStrAnnee(str))

  override def toSubStrAnnee(str: String): String =
    Option(str).map(_.substring(0, 4)).getOrElse("null")
}
