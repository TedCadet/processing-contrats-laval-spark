package contrats

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object ContratUserDefinedFunctions {

  // definition des UserDefinedFunction
  val toBigDecimal: UserDefinedFunction = udf((x: Double) => BigDecimal(x))
  val toSubStrAnneeUdf: UserDefinedFunction = udf((str: String) => toSubStrAnnee(str))

  def toSubStrAnnee(str: String): String =
    Option(str).map(_.substring(0, 4)).getOrElse("null")

}
