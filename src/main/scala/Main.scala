import ContratUserDefinedFunctions.{toBigDecimal, toSubStrAnneeUdf}
import org.apache.spark.sql.catalyst.dsl.expressions.{DslExpression, StringToAttributeConversionHelper}
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.{col, desc, udf}
import org.apache.spark.sql.{Column, DataFrame, Dataset, Row, SparkSession, functions}

object Main extends App {

  // creation du sparkSession
  val sparkSession: SparkSession = SparkSession.builder
    .appName("file-json-processing-test")
    .master("local[1]")
    .getOrCreate()

  // obtention des contrats a partir du ficher JSON
  val path: String = "contrats-octroyes.json"

  val contrats: DataFrame = sparkSession
    .read
    .option("multiline", "true")
    .json(path)

//  contrats.printSchema()

  // definition des colonnes
  val colMontant: Column = col("montant")
  val colBigMontant: Column = toBigDecimal(col("montant"))
  val colContractant: Column = col("contractant")
  val colDate: Column = col("date")
  val colNature: Column = col("nature")
  val colNoReference: Column = col("no-reference")
  val colAnne: Column = toSubStrAnneeUdf(colDate)

  //liste des contractants
  val listeContractants: DataFrame = contrats.select(colContractant).distinct


  // montant cumulatif pour chacun des contractants
  val montantCumulatifParContractants: DataFrame =
    contrats
      .select(colContractant, colMontant)
      //TODO: voir comment remplacer l'expression en utilisant directement la colonne
      .where(" nature like '%construction%'")
      .groupBy(colContractant)
      .agg(functions.sum(colMontant).as("montant cumulatif(par consultant)"))
      .orderBy(toBigDecimal(col("montant cumulatif(par consultant)")).desc)

  // argent depensee par annee
  val montantDepenseParAnnee: DataFrame =
    contrats
      .select(colDate, colMontant)
      .groupBy(colAnne)
      .agg(functions.sum(colMontant).as("montant cumulatif(par annee)"))
      .orderBy(colAnne)

  // show les resultats
  listeContractants.show()
  montantCumulatifParContractants.show()
  montantDepenseParAnnee.show()

  // fermeture de la session
  sparkSession.close()
}
