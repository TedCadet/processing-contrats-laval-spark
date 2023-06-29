package com.tedcadet.donneeslaval.contrats.grpc

import com.tedcadet.donneeslaval.contrats.{ListeContractantsReply, MontantCumulatifParContractantsReply, MontantDepenseParAnneeReply}
import org.apache.spark.sql.Row

object ContratServiceGrpcMapper {
  val listeContractantMapper: Row => ListeContractantsReply =
    row => ListeContractantsReply(row.toString())

  // valeur 0: contractant, valeur 1: montant cumulatif par consultant
  val montantCumulatifParContractantsMapper: Row => MontantCumulatifParContractantsReply =
    row => MontantCumulatifParContractantsReply(row.getAs(0), row.getAs(1).toString)

  // valeur 0: annee, valeur 1: montant depense par annee
  val montantDepenseParAnneeMapper: Row => MontantDepenseParAnneeReply =
    row => MontantDepenseParAnneeReply(row.getAs(0), row.getAs(1).toString)
}
