package com.crm.miniCRM.dto;

import com.crm.miniCRM.model.persistence.MemberID;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class MemberDto {

    private MemberID Id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate since;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate until;//default 9999-21-31
    private Boolean active;

    public MemberDto(){}

    public MemberDto(MemberID id, LocalDate since, LocalDate until) {
        Id = id;
        this.since = since;
        this.until = until;
        this.active = true;
    }
    public MemberDto (MemberID id, LocalDate since){
        this(id, since, LocalDate.of(9999,12,31));
    }

    public MemberID getId() {
        return Id;
    }

    public void setId(MemberID id) {
        Id = id;
    }

    public LocalDate getSince() {
        return since;
    }

    public void setSince(LocalDate since) {
        this.since = since;
    }

    public LocalDate getUntil() {
        return until;
    }

    public void setUntil(LocalDate until) {
        this.until = until;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
