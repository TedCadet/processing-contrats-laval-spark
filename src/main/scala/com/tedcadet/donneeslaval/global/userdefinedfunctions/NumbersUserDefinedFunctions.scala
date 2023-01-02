package com.tedcadet.donneeslaval.global.userdefinedfunctions

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object NumbersUserDefinedFunctions {
  val toBigDecimal: UserDefinedFunction = udf((x: Double) => BigDecimal(x))
}
