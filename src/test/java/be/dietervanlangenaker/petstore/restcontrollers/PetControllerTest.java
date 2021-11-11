package be.dietervanlangenaker.petstore.restcontrollers;

import be.dietervanlangenaker.petstore.domain.Category;
import be.dietervanlangenaker.petstore.domain.Pet;
import be.dietervanlangenaker.petstore.domain.Status;
import be.dietervanlangenaker.petstore.services.DefaultPetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static groovy.json.JsonOutput.toJson;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

@Sql("/insertTestPets.sql")
class PetControllerTest  extends AbstractTransactionalJUnit4SpringContextTests {
    @Mock
    private DefaultPetService petService;
    private final MockMvc mvc;
    PetControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }
    private long idTestPet1() {
        return jdbcTemplate.queryForObject("select id from pets where name = 'test1'", long.class);
    }

    @Test
    void  unknownPetGivesStatusNotFound() throws Exception{
        mvc.perform(get("/pet/{id}",-1))
                        .andExpect(status().isNotFound());
    }
    @Test
    void readPetByIdGivesStatusOk() throws Exception{
        mvc.perform(get("/pet/{id}",idTestPet1()))
                        .andExpect(status().isOk());
    }
    @Test
    void ReadPetByIdGivesContentTypeJSON()throws Exception{
        mvc.perform(get("/pet/{id}",idTestPet1()))
                        .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json;"));
    }
    @Test
    void readPetByIdMatchesTestPetId() throws Exception{
        mvc.perform(get("/pet/{id}",idTestPet1()))
                        .andExpect(jsonPath("id")
                        .value(idTestPet1()));
    }

    @Test
    public void postNewPetGivesStatusCreated() throws Exception {
        Pet pet = new Pet(333,"MockingPet",new Category(1,"Mockingbird"),"www.birds.com",Status.SOLD,null);
        mvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(pet)))
                        .andExpect(status().isCreated());
    }
    @Test
    public void postUnvalidPetWithNoPetNameGivesStatusBadRequest() throws Exception {
        mvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{'id': 1, 'photoUrls': 'string','status': 'AVAILABLE'}]"))
                        .andExpect(status().isBadRequest());
    }
    @Test
    public void putPetNameGivesStatusOkAndHasSameID() throws Exception {
        Pet pet = new Pet(500,"MockingPet", new Category(1,"Kat"),"www.birds.com",Status.SOLD,null);
        Pet putPet = new Pet(500,"MockingPetChange", new Category(1,"Kat"),"www.birds.com",Status.SOLD,null);
        mvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(pet)))
                        .andExpect(status().isCreated());
        mvc.perform(get("/pet/500"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("name").value("MockingPet"));
        mvc.perform(put("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(putPet)))
                        .andExpect(status().isOk());
        mvc.perform(get("/pet/500"))
                        .andExpect(jsonPath("name").value("MockingPetChange"));
    }

    @Test
    public void putNonExistingPetGivesStatusNotFound() throws Exception {
        Pet putPet = new Pet(-2,"MockingPetChange", new Category(1,"Kat"),"www.birds.com",Status.SOLD,null);
        mvc.perform(put("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(putPet)))
                .andExpect(status().isNotFound());
    }
    @Test
    public void deletePetGivesStatusOkAndGetDeletedPetGivesNotFound() throws Exception {
        mvc.perform(delete("/pet/"+idTestPet1()))
                        .andExpect(status().isOk());
        mvc.perform(get("/pet/"+idTestPet1()))
                        .andExpect(status().isNotFound());
    }

    @Test
    public void postNewPetCanOnlyHaveStatusPENDINGOrSOLDOrAVAILABLE() throws Exception {
        //Pet pet = new Pet(333,"MockingPet",new Category(1,"Mockingbird"),"www.birds.com",Status.SOLD,null);
        mvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":9,\"name\":\"Dalida\",\"category\":{\"id\":1,\"name\":\"kat\"},\"photourls\":\"https://placekitten.com/g/200/300\",\"tags\":[],\"status\":\"FAULT\"}"))
                        .andExpect(status().isBadRequest());
        mvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":9,\"name\":\"Dalida\",\"category\":{\"id\":1,\"name\":\"kat\"},\"photourls\":\"https://placekitten.com/g/200/300\",\"tags\":[],\"status\":\"AVAILABLE\"}"))
                .andExpect(status().isCreated());
        mvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":9,\"name\":\"Dalida\",\"category\":{\"id\":1,\"name\":\"kat\"},\"photourls\":\"https://placekitten.com/g/200/300\",\"tags\":[],\"status\":\"SOLD\"}"))
                .andExpect(status().isCreated());
        mvc.perform(post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":9,\"name\":\"Dalida\",\"category\":{\"id\":1,\"name\":\"kat\"},\"photourls\":\"https://placekitten.com/g/200/300\",\"tags\":[],\"status\":\"SOLD\"}"))
                .andExpect(status().isCreated());
    }



}