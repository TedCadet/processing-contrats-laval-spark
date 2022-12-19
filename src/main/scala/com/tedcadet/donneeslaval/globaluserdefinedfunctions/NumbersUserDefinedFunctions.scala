package com.tedcadet.donneeslaval.globaluserdefinedfunctions

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

import java.math.MathContext
import scala.math.BigDecimal.RoundingMode

trait NumbersUserDefinedFunctions {
  val toBigDecimal: UserDefinedFunction = udf((x: Double) => BigDecimal(x))
}
