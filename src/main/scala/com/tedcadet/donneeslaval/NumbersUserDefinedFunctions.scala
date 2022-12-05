package com.tedcadet.donneeslaval

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

trait NumbersUserDefinedFunctions {
  val toBigDecimal: UserDefinedFunction = udf((x: Double) => BigDecimal(x))
}
