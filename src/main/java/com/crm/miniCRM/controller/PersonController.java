package com.crm.miniCRM.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import com.crm.miniCRM.dto.PersonDto;
import com.crm.miniCRM.model.Person;
import com.crm.miniCRM.model.persistence.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/persons")
public class PersonController {

    private PersonRepository personService;

    public PersonController(PersonRepository personService) {
        this.personService = personService;
    }

    @GetMapping
    public String getpersons(Model model) {
        Iterable<Person> persons = personService.findAll();
        List<PersonDto> personDtos = new ArrayList<>();
        for(Person person: persons){
            if(person.getActive()){
                personDtos.add(convertToDto(person));
            }
        }
        model.addAttribute("persons", personDtos);
        return "persons";
    }

    @GetMapping("/new")
    public String newperson(Model model) {
        model.addAttribute("person", new PersonDto());
        return "new-person";
    }

    @PostMapping
    public String addperson(PersonDto person) {
        personService.save(convertToEntity(person));

        return "redirect:/persons";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Person person = personService.findById(id);
        if(person == null){
            new IllegalArgumentException("Invalid user Id:" + id);
        }
        model.addAttribute("person", person);
        return "update-person";
    }

    @PostMapping("/update/{id}")
    public String updatePerson(@PathVariable("id") long id, Person person,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            person.setId(id);
            return "update-person";
        }

        personService.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") long id, Model model) {
        Person person = personService.findById(id);
        person.setActive(false);

        personService.save(person);
        return "redirect:/persons";
    }

    protected PersonDto convertToDto(Person entity) {
        PersonDto dto = new PersonDto(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getBirthDay().toString());
         return dto;
    }

    protected Person convertToEntity(PersonDto dto) {
        int year = Integer.parseInt(dto.getBirthDay().toString().substring(6,10));
        int month = Integer.parseInt(dto.getBirthDay().toString().substring(3,5));
        int day = Integer.parseInt(dto.getBirthDay().toString().substring(0,2));
        Person person = new Person(dto.getFirstName(), dto.getLastName(), LocalDate.of(year, month, day));
        if (!StringUtils.isEmpty(dto.getId())) {
            person.setId(dto.getId());
        }
        return person;
    }

    public String getInfo(long id){
        Person person = personService.findById(id);
        return  person.getFirstName() + " " + person.getLastName();
    }

}
