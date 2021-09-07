package com.crm.miniCRM.model;

import com.crm.miniCRM.model.persistence.MemberID;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="member")
public class Member {
    @EmbeddedId
    private MemberID Id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate since;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate until;//default 9999-21-31
    private Boolean active;

    public Member(){}
    public Member(MemberID id, LocalDate since, LocalDate until) {
        Id = id;
        this.since = since;
        this.until = until;
        active = true;
    }
    public Member (MemberID id, LocalDate since){
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

    @Override
    public String toString() {
        return "Member{" +
                "Id=" + Id +
                ", since=" + since +
                ", until=" + until +
                '}';
    }
}
