package be.dietervanlangenaker.petstore.repositories;

import be.dietervanlangenaker.petstore.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
