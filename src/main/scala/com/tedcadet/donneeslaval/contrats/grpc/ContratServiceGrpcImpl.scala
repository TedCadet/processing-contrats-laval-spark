package com.tedcadet.donneeslaval.contrats.grpc

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.tedcadet.donneeslaval.contrats.spark.ContratSparkService
import com.tedcadet.donneeslaval.contrats.{ContratService, ListeContractantsReply, ListeContractantsRequest, MontantCumulatifParContractantsReply, MontantCumulatifParContractantsRequest, MontantDepenseParAnneeReply, MontantDepenseParAnneeRequest}

class ContratServiceGrpcImpl extends ContratService {
  override def listeContractants(in: ListeContractantsRequest): Source[ListeContractantsReply, NotUsed] =
    Source(ContratSparkService.listeContractants.collect())
      .map(ContratServiceGrpcMapper.listeContractantMapper)

  override def montantCumulatifParContractants(in: MontantCumulatifParContractantsRequest):
  Source[MontantCumulatifParContractantsReply, NotUsed] =
    Source(ContratSparkService.montantCumulatifParContractants.collect())
      .map(ContratServiceGrpcMapper.montantCumulatifParContractantsMapper)

  override def montantDepenseParAnnee(in: MontantDepenseParAnneeRequest):
  Source[MontantDepenseParAnneeReply, NotUsed] =
    Source(ContratSparkService.montantDepenseParAnnee.collect())
      .map(ContratServiceGrpcMapper.montantDepenseParAnneeMapper)
}


