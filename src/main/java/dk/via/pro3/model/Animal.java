package dk.via.pro3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "animals")
public class Animal {

  @Id
  @Column(name = "registration_number")
  private String registrationNumber;

  @Column(name = "weight")
  private double weight;

  public Animal() {
  }

  public Animal(String registrationNumber, double weight) {
    this.registrationNumber = registrationNumber;
    this.weight = weight;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }
}