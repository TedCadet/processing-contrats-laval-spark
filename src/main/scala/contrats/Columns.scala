package contrats

import contrats.ContratUserDefinedFunctions.{toBigDecimal, toSubStrAnneeUdf}
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions.col

object Columns {
  // definition des colonnes
  val colMontant: Column = col("montant")
  val colBigMontant: Column = toBigDecimal(col("montant"))
  val colContractant: Column = col("contractant")
  val colDate: Column = col("date")
  val colNature: Column = col("nature")
  val colNoReference: Column = col("no-reference")
  val colAnne: Column = toSubStrAnneeUdf(colDate)
}
