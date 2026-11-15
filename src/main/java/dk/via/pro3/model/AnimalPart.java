package dk.via.pro3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "animal_parts")
public class AnimalPart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "part_type")
  private String partType;

  @Column(name = "weight")
  private double weight;

  @Column(name = "animal_registration_number")
  private String animalRegistrationNumber;

  @Column(name = "tray_id")
  private Long trayId;

  public AnimalPart() {
  }

  public AnimalPart(Long id, String partType, double weight, String animalRegistrationNumber, Long trayId) {
    this.id = id;
    this.partType = partType;
    this.weight = weight;
    this.animalRegistrationNumber = animalRegistrationNumber;
    this.trayId = trayId;
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

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public String getAnimalRegistrationNumber() {
    return animalRegistrationNumber;
  }

  public void setAnimalRegistrationNumber(String animalRegistrationNumber) {
    this.animalRegistrationNumber = animalRegistrationNumber;
  }

  public Long getTrayId() {
    return trayId;
  }

  public void setTrayId(Long trayId) {
    this.trayId = trayId;
  }
}