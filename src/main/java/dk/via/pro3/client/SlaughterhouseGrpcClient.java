package dk.via.pro3.client;

import dk.via.pro3.pro3assignment.grpc.AnimalListResponse;
import dk.via.pro3.pro3assignment.grpc.AnimalRequest;
import dk.via.pro3.pro3assignment.grpc.ProductListResponse;
import dk.via.pro3.pro3assignment.grpc.ProductRequest;
import dk.via.pro3.pro3assignment.grpc.SlaughterhouseQueryServiceGrpc;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SlaughterhouseGrpcClient {

  private final SlaughterhouseQueryServiceGrpc.SlaughterhouseQueryServiceBlockingStub stub;

  public SlaughterhouseGrpcClient(GrpcChannelFactory channelFactory) {
    this.stub = SlaughterhouseQueryServiceGrpc.newBlockingStub(
        channelFactory.createChannel("localhost:9090")
    );
  }

  public List<String> getAnimalsForProduct(long productId) {
    ProductRequest request = ProductRequest.newBuilder()
        .setProductId(productId)
        .build();

    AnimalListResponse response = stub.getAnimalsForProduct(request);
    return response.getRegistrationNumbersList();
  }

  public List<Long> getProductsForAnimal(String registrationNumber) {
    AnimalRequest request = AnimalRequest.newBuilder()
        .setRegistrationNumber(registrationNumber)
        .build();

    ProductListResponse response = stub.getProductsForAnimal(request);
    return response.getProductIdsList();
  }
}