package com.crm.miniCRM.controller;

import com.crm.miniCRM.dto.CommunityDto;
import com.crm.miniCRM.model.Community;
import com.crm.miniCRM.model.Person;
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
@RequestMapping(value = "/communities")
public class CommunityController {

    private CommunityRepository communityService;

    public CommunityController(CommunityRepository communityService) {
        this.communityService = communityService;
    }

    @GetMapping
    public String getcommunities(Model model) {
        Iterable<Community> communities = communityService.findAll();
        List<CommunityDto> CommunityDtos = new ArrayList<>();
        for(Community community: communities) {
            if (community.getActive()) {
                CommunityDtos.add(convertToDto(community));
            }
        }
        model.addAttribute("communities", CommunityDtos);
        return "communities";
    }

    @GetMapping("/new")
    public String newcommunity(Model model) {
        model.addAttribute("community", new CommunityDto());
        return "new-community";
    }

    @PostMapping
    public String addcommunity(CommunityDto community) {
        communityService.save(convertToEntity(community));

        return "redirect:/communities";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Community community = communityService.findById(id);
        if(community == null){
            new IllegalArgumentException("invalid community id:" + id);
        }
        model.addAttribute("communities", community);
        return "update-community";
    }

    @PostMapping("/update/{id}")
    public String updateCommunity(@PathVariable("id") long id, Community community,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            community.setID(id);
            return "update-community";
        }

        community.setActive(true);
        communityService.save(community);
        return "redirect:/communities";
    }

    @GetMapping("/delete/{id}")
    public String deleteCommunity(@PathVariable("id") long id, Model model) {
        Community community = communityService.findById(id);
        community.setActive(false);

        communityService.save(community);
        return "redirect:/communities";
    }

    protected CommunityDto convertToDto(Community entity) {
        CommunityDto dto = new CommunityDto(entity.getID(), entity.getDescription());
         return dto;
    }

    protected Community convertToEntity(CommunityDto dto) {
        //29-06-1963

        Community community = new Community(dto.getDescription());
        if (!StringUtils.isEmpty(dto.getId())) {
            community.setID(dto.getId());
        }
        return community;
    }

    public String getInfo(long id){
        Community community = communityService.findById(id);
        return community.getDescription();
    }

}
