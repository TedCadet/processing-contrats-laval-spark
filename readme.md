# Pour tester le service GRPC

*Préalable installer grpcurl*

## liste des contractants

```shell
grpcurl -proto ./src/main/protobuf/contratslaval.proto -plaintext 127.0.0.1:8080 ContratService/ListeContractants
```

## montant cumulatif par contractants

```shell
grpcurl -proto ./src/main/protobuf/contratslaval.proto -plaintext 127.0.0.1:8080 ContratService/MontantCumulatifParContractants
```