package com.crm.miniCRM.controller;

import com.crm.miniCRM.dto.PersonAddressDto;
import com.crm.miniCRM.model.Address;
import com.crm.miniCRM.model.Member;
import com.crm.miniCRM.model.Person;
import com.crm.miniCRM.model.PersonAddress;
import com.crm.miniCRM.model.persistence.AddressRepository;
import com.crm.miniCRM.model.persistence.PersonAddressID;
import com.crm.miniCRM.model.persistence.PersonAddressRepository;
import com.crm.miniCRM.model.persistence.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "/personaddress")
public class PersonAddressController {

    private PersonAddressRepository personAddressRepository;
    private PersonRepository personRepository;
    private AddressRepository addressRepository;
    private PersonController personController;
    private AddressesController addressesController;
    //temp personaddress om duplicate weg te halen. Want als id's gewijzigd zijn word dit als nieuw obj gezien ?
    private PersonAddress temp;

    public PersonAddressController(PersonAddressRepository personService, PersonRepository personRepository, AddressRepository addressRepository) {
        this.personAddressRepository = personService;
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.personController = new PersonController(personRepository);
        this.addressesController = new AddressesController(addressRepository);
    }

    @GetMapping
    public String getpersonAddress(Model model) {
        Iterable<PersonAddress> personAddresses = personAddressRepository.findAll();
        List<PersonAddressDto> personDtos = new ArrayList<>();
        for(PersonAddress person: personAddresses){
            if(person.getActive()){
                personDtos.add(convertToDto(person));
            }
        }
        model.addAttribute("personaddress", personDtos);
        model.addAttribute("personlist", personController);
        model.addAttribute("address", addressesController);
        return "personaddress";
    }

    @GetMapping("/new")
    public String newpersonAddress(Model model) {
        Iterable<Person> persons = personRepository.findAll();
        Iterable<Address> addresses = addressRepository.findAll();
        model.addAttribute("personaddress", new PersonAddressDto());
        model.addAttribute("person", persons);
        model.addAttribute("address", addresses);
        return "new-personaddress";
    }

    @PostMapping
    public String addpersonAddress(PersonAddressDto person) {
        personAddressRepository.save(convertToEntity(person));

        return "redirect:/personaddress";
    }

    @RequestMapping(value = "/edit/{id}", method= RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") List<Long> ids, Model model) {
        PersonAddressID personAddressID = new PersonAddressID(ids.get(0), ids.get(1));
        PersonAddress person = personAddressRepository.findById(personAddressID).get();
        Iterable<Person> persons = personRepository.findAll();
        Iterable<Address> addresses = addressRepository.findAll();
        if(person == null){
            new IllegalArgumentException("Invalid user Id:" + ids.get(0) + ids.get(1));
        }
        this.temp = person;
        model.addAttribute("personaddress", person);
        model.addAttribute("person", persons);
        model.addAttribute("address", addresses);
        return "update-personaddress";
    }

    @RequestMapping(value = "/update/{id}", method= RequestMethod.POST)
    public String updatePersonAddress(@PathVariable("id") List<Long> ids, PersonAddress person,
                             BindingResult result, Model model) {
        /* Dit geeft een fout ? Mogelijk door de meerdere ID's. Na veel debuggen nog geen oplossing voor gevonden.
        if (result.hasErrors()) {
            person.setId(person.getId());
            return "update-personaddress";
        }*/
        person.setActive(true);
        personAddressRepository.save(person);
        //duplicate weghalen
        personAddressRepository.delete(this.temp);
        return "redirect:/personaddress";
    }

    @RequestMapping(value = "/delete/{id}", method= RequestMethod.GET)
    public String deletePerson(@PathVariable("id") List<Long> ids, Model model) {
        PersonAddressID personAddressID = new PersonAddressID(ids.get(0), ids.get(1));
        PersonAddress person = personAddressRepository.findById(personAddressID).get();
        person.setActive(false);

        personAddressRepository.save(person);
        return "redirect:/personaddress";
    }

    protected PersonAddressDto convertToDto(PersonAddress entity) {
        PersonAddressDto dto = new PersonAddressDto(entity.getId(), entity.getEmail(), entity.getPhone(), entity.getMobile(), entity.getType(), entity.getPreference());
         return dto;
    }

    protected PersonAddress convertToEntity(PersonAddressDto dto) {
        PersonAddress person = new PersonAddress(dto.getId(), dto.getEmail(), dto.getPhone(), dto.getMobile(), dto.getType(), dto.getPreference());
        if (!StringUtils.isEmpty(dto.getId())) {
            person.setId(dto.getId());
        }
        return person;
    }
}
