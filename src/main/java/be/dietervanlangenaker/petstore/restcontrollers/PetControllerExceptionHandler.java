package be.dietervanlangenaker.petstore.restcontrollers;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(assignableTypes = PetController.class)
public class PetControllerExceptionHandler {

}
