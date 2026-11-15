package dk.via.pro3.service;

import dk.via.pro3.model.AnimalPart;
import dk.via.pro3.model.Product;
import dk.via.pro3.pro3assignment.grpc.AnimalListResponse;
import dk.via.pro3.pro3assignment.grpc.AnimalRequest;
import dk.via.pro3.pro3assignment.grpc.ProductListResponse;
import dk.via.pro3.pro3assignment.grpc.ProductRequest;
import dk.via.pro3.pro3assignment.grpc.SlaughterhouseQueryServiceGrpc;
import dk.via.pro3.repository.AnimalPartRepository;
import dk.via.pro3.repository.AnimalRepository;
import dk.via.pro3.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;
import java.util.Optional;

@GrpcService
public class GrpcSlaughterhouseServer extends SlaughterhouseQueryServiceGrpc.SlaughterhouseQueryServiceImplBase {

  private final AnimalRepository animalRepository;
  private final AnimalPartRepository animalPartRepository;
  private final ProductRepository productRepository;

  public GrpcSlaughterhouseServer(
      AnimalRepository animalRepository,
      AnimalPartRepository animalPartRepository,
      ProductRepository productRepository) {
    this.animalRepository = animalRepository;
    this.animalPartRepository = animalPartRepository;
    this.productRepository = productRepository;
  }

  @Override
  public void getAnimalsForProduct(ProductRequest request, StreamObserver<AnimalListResponse> responseObserver) {
    Optional<Product> productOpt = productRepository.findById(request.getProductId());

    List<String> registrationNumbers = productOpt
        .map(product -> animalPartRepository.findByTrayId(product.getTrayId()).stream()
            .map(AnimalPart::getAnimalRegistrationNumber)
            .distinct()
            .toList())
        .orElse(List.of());

    AnimalListResponse response = AnimalListResponse.newBuilder()
        .addAllRegistrationNumbers(registrationNumbers)
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public void getProductsForAnimal(AnimalRequest request, StreamObserver<ProductListResponse> responseObserver) {
    List<Long> trayIds = animalPartRepository.findByAnimalRegistrationNumber(request.getRegistrationNumber()).stream()
        .map(AnimalPart::getTrayId)
        .distinct()
        .toList();

    List<Long> productIds = trayIds.stream()
        .flatMap(trayId -> productRepository.findByTrayId(trayId).stream())
        .map(Product::getId)
        .distinct()
        .toList();

    ProductListResponse response = ProductListResponse.newBuilder()
        .addAllProductIds(productIds)
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}