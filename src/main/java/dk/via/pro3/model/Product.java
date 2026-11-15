package dk.via.pro3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_type")
  private String productType;

  @Column(name = "tray_id")
  private Long trayId;

  public Product() {
  }

  public Product(Long id, String productType, Long trayId) {
    this.id = id;
    this.productType = productType;
    this.trayId = trayId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public Long getTrayId() {
    return trayId;
  }

  public void setTrayId(Long trayId) {
    this.trayId = trayId;
  }
}