package com.tedcadet.donneeslaval

import com.tedcadet.donneeslaval.contrats.spark.ContratSparkService.{listeContractantParNature, listeContractants, listeDesNaturesDeContrat, montantCumulatifParContractants, montantDepenseParAnnee, montantOctroyeParNatures}

object SparkQueryTester extends App {

// show les resultats
//  listeParColonneDistinctQuery(contrats,colNoReference).show()
  listeContractants.show()
  montantCumulatifParContractants.show()
  montantDepenseParAnnee.show()
  listeDesNaturesDeContrat.show()
  montantOctroyeParNatures.show()
  listeContractantParNature.show()
//  println(s"nombre de contrats: ${contrats.count()}")


  montantCumulatifParContractants.printSchema()
  montantDepenseParAnnee.printSchema()

//  val contractants = listeContractants.as[Contractant].first().toString

  // fermeture de la session
//  closeSession()
}
