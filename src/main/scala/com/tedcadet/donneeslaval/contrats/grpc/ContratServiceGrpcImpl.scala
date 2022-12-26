package com.tedcadet.donneeslaval.contrats.grpc

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.tedcadet.donneeslaval.contrats.spark.ContratSparkService
import com.tedcadet.donneeslaval.contrats.{ContratService, ListeContractantsReply, ListeContractantsRequest}
import org.apache.spark.sql.Row
import scalapb.GeneratedMessage

class ContratServiceGrpcImpl extends ContratService {
  override def listeContractants(in: ListeContractantsRequest): Source[ListeContractantsReply, NotUsed] =
    Source(ContratSparkService.listeContractants.collect())
      .map(listeContractantMapper)

  val listeContractantMapper: Row => ListeContractantsReply = row => ListeContractantsReply(row.toString())
}


