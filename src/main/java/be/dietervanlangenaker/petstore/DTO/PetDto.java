package be.dietervanlangenaker.petstore.DTO;

import be.dietervanlangenaker.petstore.domain.Category;
import be.dietervanlangenaker.petstore.domain.Pet;
import be.dietervanlangenaker.petstore.domain.Tag;
import lombok.Data;

import java.util.Set;

@Data
public class PetDto {
private long id;
private String name;
private Category category;
private String photourls;
private String status;
private Set<Tag> tags;

    public PetDto(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.category = pet.getCategory();
        this.photourls = pet.getPhotourls();
        this.status = pet.getStatus().toString();
        this.tags=pet.getTags();
    }
}
