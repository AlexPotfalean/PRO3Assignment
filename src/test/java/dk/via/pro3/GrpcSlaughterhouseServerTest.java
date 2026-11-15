package dk.via.pro3;

import dk.via.pro3.model.Animal;
import dk.via.pro3.model.AnimalPart;
import dk.via.pro3.model.Product;
import dk.via.pro3.model.Tray;
import dk.via.pro3.pro3assignment.grpc.AnimalListResponse;
import dk.via.pro3.pro3assignment.grpc.AnimalRequest;
import dk.via.pro3.pro3assignment.grpc.ProductListResponse;
import dk.via.pro3.pro3assignment.grpc.ProductRequest;
import dk.via.pro3.repository.AnimalPartRepository;
import dk.via.pro3.repository.AnimalRepository;
import dk.via.pro3.repository.ProductRepository;
import dk.via.pro3.repository.TrayRepository;
import dk.via.pro3.service.GrpcSlaughterhouseServer;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GrpcSlaughterhouseServerTest {

  @Autowired
  private GrpcSlaughterhouseServer grpcServer;

  @Autowired
  private AnimalRepository animalRepository;

  @Autowired
  private TrayRepository trayRepository;

  @Autowired
  private AnimalPartRepository animalPartRepository;

  @Autowired
  private ProductRepository productRepository;

  private Long productId;

  @BeforeEach
  void setUp() {
    productRepository.deleteAll();
    animalPartRepository.deleteAll();
    trayRepository.deleteAll();
    animalRepository.deleteAll();

    // Station 1: Register and weigh animals
    Animal a1 = animalRepository.save(new Animal("A-101", 320.5));
    Animal a2 = animalRepository.save(new Animal("A-102", 295.0));

    // Station 2: Create capacity-managed tray and register parts referencing animal + tray
    Tray tray = trayRepository.save(new Tray(null, "LEG", 500.0));
    animalPartRepository.save(new AnimalPart(null, "LEG", 85.0, a1.getRegistrationNumber(), tray.getId()));
    animalPartRepository.save(new AnimalPart(null, "LEG", 90.0, a2.getRegistrationNumber(), tray.getId()));

    // Station 3: Pack product referencing tray
    Product product = productRepository.save(new Product(null, "SUPERMARKET_PACK", tray.getId()));
    this.productId = product.getId();
  }

  @Test
  void testGetAnimalsForProduct() {
    ProductRequest request = ProductRequest.newBuilder()
        .setProductId(productId)
        .build();

    AtomicReference<AnimalListResponse> responseRef = new AtomicReference<>();
    List<Throwable> errors = new ArrayList<>();

    grpcServer.getAnimalsForProduct(request, new StreamObserver<>() {
      @Override
      public void onNext(AnimalListResponse value) {
        responseRef.set(value);
      }

      @Override
      public void onError(Throwable t) {
        errors.add(t);
      }

      @Override
      public void onCompleted() {
      }
    });

    assertTrue(errors.isEmpty(), "Expected no gRPC errors");
    AnimalListResponse response = responseRef.get();
    assertEquals(2, response.getRegistrationNumbersCount());
    assertTrue(response.getRegistrationNumbersList().containsAll(List.of("A-101", "A-102")));
  }

  @Test
  void testGetProductsForAnimal() {
    AnimalRequest request = AnimalRequest.newBuilder()
        .setRegistrationNumber("A-101")
        .build();

    AtomicReference<ProductListResponse> responseRef = new AtomicReference<>();
    List<Throwable> errors = new ArrayList<>();

    grpcServer.getProductsForAnimal(request, new StreamObserver<>() {
      @Override
      public void onNext(ProductListResponse value) {
        responseRef.set(value);
      }

      @Override
      public void onError(Throwable t) {
        errors.add(t);
      }

      @Override
      public void onCompleted() {
      }
    });

    assertTrue(errors.isEmpty(), "Expected no gRPC errors");
    ProductListResponse response = responseRef.get();
    assertEquals(1, response.getProductIdsCount());
    assertEquals(productId, response.getProductIds(0));
  }
}