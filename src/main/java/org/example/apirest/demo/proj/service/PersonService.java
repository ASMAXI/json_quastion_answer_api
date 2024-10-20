package org.example.apirest.demo.proj.service;
import org.example.apirest.demo.proj.dto.PersonDTO;
import org.example.apirest.demo.proj.model.Person;
import org.example.apirest.demo.proj.repository.*;
import org.example.apirest.demo.proj.utill.PersonNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public List<Person> findAll() {
        return personRepository.findAll();
    }
    public Person findById(int id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElseThrow(PersonNotFoundException::new);
    }

    public void save(Person person) {
        enrichPerson(person);
        personRepository.save(person);
    }

    public void update(Person person) {
        person.setUpdated_at(LocalDateTime.now());
        person.setCreated_who("ADMIN");
        personRepository.save(person);
    }


    private void enrichPerson(Person person) {
        person.setCreated_at(LocalDateTime.now());
        person.setUpdated_at(LocalDateTime.now());
        person.setCreated_who("ADMIN");
    }
}
