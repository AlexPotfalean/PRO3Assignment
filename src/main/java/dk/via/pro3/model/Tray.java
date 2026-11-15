package dk.via.pro3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trays")
public class Tray {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "part_type")
  private String partType;

  @Column(name = "max_weight_capacity")
  private double maxWeightCapacity;

  public Tray() {
  }

  public Tray(Long id, String partType, double maxWeightCapacity) {
    this.id = id;
    this.partType = partType;
    this.maxWeightCapacity = maxWeightCapacity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPartType() {
    return partType;
  }

  public void setPartType(String partType) {
    this.partType = partType;
  }

  public double getMaxWeightCapacity() {
    return maxWeightCapacity;
  }

  public void setMaxWeightCapacity(double maxWeightCapacity) {
    this.maxWeightCapacity = maxWeightCapacity;
  }
}