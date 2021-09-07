package com.crm.miniCRM.controller;

import com.crm.miniCRM.dto.MemberDto;
import com.crm.miniCRM.dto.PersonAddressDto;
import com.crm.miniCRM.model.*;
import com.crm.miniCRM.model.persistence.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = "/member")
public class MemberController {

    private MemberRepository memberRepository;
    private PersonRepository personRepository;
    private CommunityRepository communityRepository;
    private CommunityController communityController;
    private PersonController personController;
    //temp member om duplicate weg te halen. Want als id's gewijzigd zijn word dit als nieuw obj gezien ?
    private Member temp;

    public MemberController(MemberRepository memberRepository, PersonRepository personRepository, CommunityRepository communityRepository) {
        this.memberRepository = memberRepository;
        this.personRepository = personRepository;
        this.communityRepository = communityRepository;
        this.communityController = new CommunityController(communityRepository);
        this.personController = new PersonController(personRepository);
    }

    @GetMapping
    public String getMember(Model model) {
        Iterable<Member> members = memberRepository.findAll();
        List<MemberDto> membersDtos = new ArrayList<>();
        for(Member member: members){
            if(member.getActive()){
                membersDtos.add(convertToDto(member));
            }
        }
        model.addAttribute("members", membersDtos);
        model.addAttribute("community", communityController);
        model.addAttribute("person", personController);
        return "member";
    }

    @GetMapping("/new")
    public String newMember(Model model) {
        Iterable<Person> persons = personRepository.findAll();
        Iterable<Community> communities = communityRepository.findAll();
        model.addAttribute("members", new MemberDto());
        model.addAttribute("person", persons);
        model.addAttribute("communities", communities);
        return "new-member";
    }

    @PostMapping
    public String addMember(MemberDto member) {
        member.setActive(true);
        if(member.getUntil() == null){
            member.setUntil(LocalDate.of(9999,12,31));
        }
        memberRepository.save(convertToEntity(member));

        return "redirect:/member";
    }

    @RequestMapping(value = "/edit/{id}", method= RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") List<Long> ids, Model model) {
        MemberID memberID = new MemberID(ids.get(0), ids.get(1));
        Member member = memberRepository.findById(memberID).get();
        Iterable<Person> persons = personRepository.findAll();
        Iterable<Community> communities = communityRepository.findAll();
        if(member == null){
            new IllegalArgumentException("Invalid member Id:" + ids.get(0) + ids.get(1));
        }
        this.temp = member;
        model.addAttribute("members", member);
        model.addAttribute("person", persons);
        model.addAttribute("communities", communities);
        return "update-member";
    }

    @RequestMapping(value = "/update/{id}", method= RequestMethod.POST)
    public String updatePersonAddress(@PathVariable("id") List<Long> ids, Member member,
                             BindingResult result, Model model) {
        /* Dit geeft een fout ? Mogelijk door de meerdere ID's. Na veel debuggen nog geen oplossing voor gevonden.
        if (result.hasErrors()) {
            member.setId(member.getId());
            return "update-member";
        }*/
        member.setActive(true);
        memberRepository.save(member);
        //duplicate weghalen
        memberRepository.delete(this.temp);
        return "redirect:/member";
    }

    @RequestMapping(value = "/delete/{id}", method= RequestMethod.GET)
    public String deletePerson(@PathVariable("id") List<Long> ids, Model model) {
        MemberID memberID = new MemberID(ids.get(0), ids.get(1));
        Member member = memberRepository.findById(memberID).get();
        member.setActive(false);

        memberRepository.save(member);
        return "redirect:/member";
    }

    protected MemberDto convertToDto(Member entity) {
        MemberDto dto = new MemberDto(entity.getId(), entity.getSince(), entity.getUntil());
         return dto;
    }

    protected Member convertToEntity(MemberDto dto) {
        Member member = new Member(dto.getId(), dto.getSince(), dto.getUntil());
        if (!StringUtils.isEmpty(dto.getId())) {
            member.setId(dto.getId());
        }
        return member;
    }
}
