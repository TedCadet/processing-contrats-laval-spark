# Context of the projet

project to analyse with spark and serve the data throuh an Akka Grpc server.
The data came from the city of laval, more precisely their contracts given to contractors in the
last decade,
and it was free to use.

# To test the service GRPC

*Préalable installer [grpcurl](https://github.com/fullstorydev/grpcurl)*

## liste des contractants

```shell
grpcurl -proto ./src/main/protobuf/contratslaval.proto -plaintext 127.0.0.1:8080 ContratService/ListeContractants
```

## montant cumulatif par contractants

```shell
grpcurl -proto ./src/main/protobuf/contratslaval.proto -plaintext 127.0.0.1:8080 ContratService/MontantCumulatifParContractants
```

## montant depense par annee

```shell
grpcurl -proto ./src/main/protobuf/contratslaval.proto -plaintext 127.0.0.1:8080 ContratService/MontantDepenseParAnnee
```
