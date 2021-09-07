package com.crm.miniCRM.dto;

import com.crm.miniCRM.model.Community;
import com.crm.miniCRM.model.persistence.CommunityID;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventDto {

    private Long id;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;
    private boolean active;
    private CommunityID communityID;


    public EventDto(){}

    public EventDto(long id,CommunityID communityID, String description, LocalDate date, LocalTime time) {
        this.id = id;
        this.communityID = communityID;
        this.description = description;
        this.date = date;
        this.time = time;
        this.active = true;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event{" +
                "ID=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                //", time=" + time +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CommunityID getCommunityID() {
        return communityID;
    }

    public void setCommunityID(CommunityID communityID) {
        this.communityID = communityID;
    }
}
