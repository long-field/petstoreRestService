package be.dietervanlangenaker.petstore.services;

import be.dietervanlangenaker.petstore.domain.Pet;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PetService {
    Optional<Pet> findById(long id);
    void create(Pet pet);
    void delete(long id);
    void update(Pet pet);
}
