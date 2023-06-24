package com.tedcadet.donneeslaval.contrats.grpc

import com.tedcadet.donneeslaval.contrats.{ListeContractantsReply, MontantCumulatifParContractantsReply}
import org.apache.spark.sql.Row

object ContratServiceGrpcMapper {
  val listeContractantMapper: Row => ListeContractantsReply =
    row => ListeContractantsReply(row.toString())

  val montantCumulatifParContractantsMapper: Row => MontantCumulatifParContractantsReply =
    row => MontantCumulatifParContractantsReply(row.getAs(0), row.getAs(1).toString)
}
