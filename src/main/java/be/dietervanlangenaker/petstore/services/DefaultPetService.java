package be.dietervanlangenaker.petstore.services;

import be.dietervanlangenaker.petstore.domain.Pet;
import be.dietervanlangenaker.petstore.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class DefaultPetService implements PetService{
    private final PetRepository petRepository;

    public DefaultPetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pet> findById(long id) {
        return petRepository.findById(id);
    }

    @Override
    @Transactional
    public void create(Pet pet){
        petRepository.save(pet);
    }

    @Override
    public void delete(long id) {
        petRepository.deleteById(id);
    }

    @Override
    public void update(Pet pet) {
        petRepository.save(pet);
    }
}
