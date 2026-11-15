package dk.via.pro3.repository;

import dk.via.pro3.model.AnimalPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalPartRepository extends JpaRepository<AnimalPart, Long> {
  List<AnimalPart> findByTrayId(Long trayId);
  List<AnimalPart> findByAnimalRegistrationNumber(String animalRegistrationNumber);
}