package com.crm.miniCRM.dto;

public class CommunityDto {

    private Long id;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CommunityDto() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommunityDto(Long id, String description) {
        this.id = id;
        this.description = description;
        this.active = true;
    }
}
