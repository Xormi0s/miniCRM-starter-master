package com.crm.miniCRM.controller;

import com.crm.miniCRM.dto.AddressesDto;
import com.crm.miniCRM.dto.CommunityDto;
import com.crm.miniCRM.model.Address;
import com.crm.miniCRM.model.Community;
import com.crm.miniCRM.model.Person;
import com.crm.miniCRM.model.persistence.AddressRepository;
import com.crm.miniCRM.model.persistence.CommunityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/addresses")
public class AddressesController {

    private AddressRepository addressRepository;

    public AddressesController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public String getAddresses(Model model) {
        Iterable<Address> addresses = addressRepository.findAll();
        List<AddressesDto> AddressesDtos = new ArrayList<>();
        for(Address address: addresses){
            if(address.getActive()){
                AddressesDtos.add(convertToDto(address));
            }
        }
        model.addAttribute("addresses", AddressesDtos);
        return "addresses";
    }

    @GetMapping("/new")
    public String newAddresses(Model model) {
        model.addAttribute("addresses", new AddressesDto());
        return "new-addresses";
    }

    @PostMapping
    public String addAddresses(AddressesDto addresses) {
        addressRepository.save(convertToEntity(addresses));

        return "redirect:/addresses";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Address address = addressRepository.findById(id);
        if(address == null){
            new IllegalArgumentException("Invalid adress Id:" + id);
        }
        model.addAttribute("address", address);
        return "update-address";
    }

    @PostMapping("/update/{id}")
    public String updateAddress(@PathVariable("id") long id, Address address,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            address.setId(id);
            return "update-address";
        }
        address.setActive(true);
        addressRepository.save(address);
        return "redirect:/addresses";
    }

    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable("id") long id, Model model) {
        Address address = addressRepository.findById(id);
        address.setActive(false);

        addressRepository.save(address);
        return "redirect:/addresses";
    }

    protected AddressesDto convertToDto(Address entity) {
        AddressesDto dto = new AddressesDto(entity.getId() ,entity.getStreet(), entity.getNumber(), entity.getBox(), entity.getZip(), entity.getCity(), entity.getCountry(), entity.getType());
         return dto;
    }

    protected Address convertToEntity(AddressesDto dto) {

        Address address = new Address(dto.getStreet(), dto.getNumber(), dto.getBox(), dto.getZip(), dto.getCity(), dto.getCountry(), dto.getType());
        if (!StringUtils.isEmpty(dto.getId())) {
            address.setId(dto.getId());
        }
        return address;
    }

    public String getInfo(long id){
        Address address = addressRepository.findById(id);
        return address.getStreet() + " " + address.getNumber();
    }

}
