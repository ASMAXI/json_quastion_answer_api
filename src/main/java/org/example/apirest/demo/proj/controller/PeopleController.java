package org.example.apirest.demo.proj.controller;


import org.example.apirest.demo.proj.dto.PersonDTO;
import org.example.apirest.demo.proj.model.Person;
import org.example.apirest.demo.proj.repository.PersonRepository;
import org.example.apirest.demo.proj.service.PersonService;
import org.example.apirest.demo.proj.utill.PersonErrorResponce;
import org.example.apirest.demo.proj.utill.PersonNotCreatedException;
import org.example.apirest.demo.proj.utill.PersonNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final ModelMapper modelMapper;
    private final PersonService personService;
    private final PersonRepository personRepository;


    @Autowired
    public PeopleController(PersonService personService, PersonRepository personRepository, ModelMapper modelMapper) {
        this.personService = personService;
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getAllPeople() {
        return personService.findAll().stream().map(this::convertToDTO).toList();// Jackson конвертирует в json
    }

    @GetMapping("/{id}")
    public PersonDTO getPeopleById(@PathVariable int id) {
            return convertToDTO(personService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createPeople(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errors.append(fieldError.getField()).append(" : ").append(fieldError.getDefaultMessage()).append("\n");
            }
            throw new PersonNotCreatedException(errors.toString());
        }
        personService.save(convertToPerson(personDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);


    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponce> handeException(PersonNotCreatedException e) {
        PersonErrorResponce responce = new PersonErrorResponce(e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler
    private ResponseEntity<PersonErrorResponce> handeException(PersonNotFoundException e) {
        PersonErrorResponce responce = new PersonErrorResponce("Person not foind", System.currentTimeMillis());
        return new ResponseEntity<>(responce, HttpStatus.NOT_FOUND);

    }

    private Person convertToPerson(PersonDTO personDTO){
//        ModelMapper modelMapper = new ModelMapper();

//        Person person = new Person();
//        person.setAge(personDTO.getAge());
//        person.setName(personDTO.getName());
//        person.setEmail(personDTO.getEmail());
        return modelMapper.map(personDTO, Person.class);


    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updatePeople(@RequestBody Person person, @PathVariable String id) {

        Person exictingPerson = personRepository.findById(person.getId()).orElseThrow(PersonNotFoundException::new);
        exictingPerson.setName(person.getName());
        exictingPerson.setAge(person.getAge());
        exictingPerson.setEmail(person.getEmail());

        personService.update(exictingPerson);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public PersonDTO convertToDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }




//    @PostMapping("/add")
//    public Person addPeople(@RequestBody Person person) {
//
//    }


}
