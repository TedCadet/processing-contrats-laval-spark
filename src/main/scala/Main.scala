import contrats.DFQueries.{listeContractantsQueries, montantCumulatifParContractantsQuery, montantDepenseParAnneeQuery}
import org.apache.spark.sql.{DataFrame, SparkSession}

object Main extends App {

  // creation du sparkSession
  val sparkSession: SparkSession = SparkSession.builder
    .appName("file-json-processing-test")
    .master("local[1]")
    .getOrCreate()

  // obtention des contrats a partir du ficher JSON
  val path: String = "src/main/scala/contrats/contrats-octroyes.json"

  val contrats: DataFrame = sparkSession
    .read
    .option("multiline", "true")
    .json(path)

//  contrats.printSchema()

  //liste des contractants
  val listeContractants: DataFrame = listeContractantsQueries(contrats)

  // montant cumulatif pour chacun des contractants
  val montantCumulatifParContractants: DataFrame = montantCumulatifParContractantsQuery(contrats)

  // argent depensee par annee
  val montantDepenseParAnnee: DataFrame = montantDepenseParAnneeQuery(contrats)

  // show les resultats
  listeContractants.show()
  montantCumulatifParContractants.show()
  montantDepenseParAnnee.show()

  // fermeture de la session
  sparkSession.close()
}
