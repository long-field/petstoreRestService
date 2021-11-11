package be.dietervanlangenaker.petstore.restcontrollers;


import be.dietervanlangenaker.petstore.domain.Pet;
import be.dietervanlangenaker.petstore.exceptions.PetNotFoundException;
import be.dietervanlangenaker.petstore.services.PetService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.TypedEntityLinks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/pet")
@CrossOrigin(exposedHeaders = "Location")
@ExposesResourceFor(Pet.class)
public class PetController {
    private PetService service;
    private final TypedEntityLinks.ExtendedTypedEntityLinks<Pet> links;
    public PetController(PetService service, EntityLinks links) {
        this.service = service;
        this.links = links.forType(Pet.class, Pet::getId);
    }

   @Operation(summary = "Find pet by ID")
   @GetMapping("/{id}")
   Optional<Pet> getPetById(@PathVariable Long id){
        if(service.findById(id).isEmpty()){throw new PetNotFoundException();}
        return service.findById(id);
    }

    //OLD
    /*@GetMapping("/{id}")
    EntityModel<PetDto> findById(@PathVariable Long id) {
        try {
            return EntityModel.of(new PetDto(service.findById(id).get()));
        } catch (Exception ex) {
            throw new PetNotFoundException();
        }
    }*/
    @Operation(summary = "Save a new pet")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    HttpHeaders create(@RequestBody @Valid Pet pet) {
        service.create(pet);
        var headers = new HttpHeaders();
        headers.setLocation(links.linkToItemResource(pet).toUri());
        return headers;
    }
    @Operation(summary = "Delete pet by ID")
    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        service.delete(id);
    }

    @Operation(summary = "Update existing pet")
    @PutMapping
    void put( @RequestBody @Valid Pet pet) {
        var petFind = service.findById(pet.getId());
        if(petFind.isEmpty()){throw new PetNotFoundException();}
        else {
        service.update(pet);}
    }
    //OLD
    /*
    @PutMapping("{id}")
    void put(@PathVariable long id,  @RequestBody @Valid Pet pet) {
        service.update(pet.withId(id));
        }

     */
    @ExceptionHandler(PetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void PetNotFound() { }

}
