package com.crm.miniCRM.controller;

import com.crm.miniCRM.dto.EventDto;
import com.crm.miniCRM.dto.MemberDto;
import com.crm.miniCRM.model.Community;
import com.crm.miniCRM.model.Event;
import com.crm.miniCRM.model.Member;
import com.crm.miniCRM.model.Person;
import com.crm.miniCRM.model.persistence.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/event")
public class EventController {

    private EventRepository eventRepository;
    private CommunityRepository communityRepository;
    private CommunityController communityController;
    //temp member om duplicate weg te halen. Want als id's gewijzigd zijn word dit als nieuw obj gezien ?
    private Member temp;

    public EventController(EventRepository eventRepository, CommunityRepository communityRepository) {
        this.eventRepository = eventRepository;
        this.communityRepository = communityRepository;
        this.communityController = new CommunityController(communityRepository);
    }

    @GetMapping
    public String getEvent(Model model) {
        Iterable<Event> events = eventRepository.findAll();
        List<EventDto> eventsDtos = new ArrayList<>();
        for(Event event: events){
            if(event.getActive()){
                eventsDtos.add(convertToDto(event));
            }
        }
        model.addAttribute("events", eventsDtos);
        model.addAttribute("community", communityController);
        return "event";
    }

    @GetMapping("/new")
    public String newEvent(Model model) {
        Iterable<Event> events = eventRepository.findAll();
        Iterable<Community> communities = communityRepository.findAll();
        model.addAttribute("events", new EventDto());
        model.addAttribute("communities", communities);
        return "new-event";
    }

    @PostMapping
    public String addEvent(EventDto event) {
        event.setActive(true);
        eventRepository.save(convertToEntity(event));

        return "redirect:/event";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Event event = eventRepository.findById(id);
        Iterable<Community> communities = communityRepository.findAll();
        if(event == null){
            new IllegalArgumentException("Invalid event Id:" + id);
        }
        model.addAttribute("events", event);
        model.addAttribute("communities", communities);
        return "update-event";
    }

    @PostMapping("/update/{id}")
    public String updateEvent(@PathVariable("id") long id, Event event,
                               BindingResult result, Model model) {
        event.setActive(true);
        if (result.hasErrors()) {
            event.setId(id);
            return "update-event";
        }

        eventRepository.save(event);
        return "redirect:/event";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") long id, Model model) {
        Event event = eventRepository.findById(id);
        event.setActive(false);

        eventRepository.save(event);
        return "redirect:/event";
    }

    protected EventDto convertToDto(Event entity) {
        EventDto dto = new EventDto(entity.getId(), entity.getCommunityID(), entity.getDescription(), entity.getDate(), entity.getTime());
         return dto;
    }

    protected Event convertToEntity(EventDto dto) {
        Event event = new Event(dto.getCommunityID(), dto.getDescription(), dto.getDate(), dto.getTime());
        if (!StringUtils.isEmpty(dto.getId())) {
            event.setId(dto.getId());
        }
        return event;
    }
}
