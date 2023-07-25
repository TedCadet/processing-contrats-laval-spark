package com.tedcadet.donneeslaval

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import com.tedcadet.donneeslaval.contrats.ContratServiceHandler
import com.tedcadet.donneeslaval.contrats.grpc.ContratServiceGrpcImpl
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}

object GrpcServer extends App {
  val conf = ConfigFactory.load()

  val system = ActorSystem("contrats-octroye-laval", conf)
  new GrpcServer(system).run()
}

class GrpcServer(system: ActorSystem) {
  def run(): Future[Http.ServerBinding] = {
    // Akka boot up code
    implicit val sys: ActorSystem = system
    implicit val ec: ExecutionContext = sys.dispatcher

    // create service handlers
    val service: HttpRequest => Future[HttpResponse] =
      ContratServiceHandler(new ContratServiceGrpcImpl())

    // Bind service handler servers to localhost:8080/8081
    val host = "0.0.0.0"
    val port = 8080
    val binding = Http().newServerAt(host, port).bind(service)

    // report successful binding
    binding.foreach {
      binding => println(s"gRPC server bound to: ${binding.localAddress}")
    }

    binding
  }
}
