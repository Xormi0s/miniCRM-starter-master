package com.crm.miniCRM.model.persistence;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class CommunityID implements Serializable {

    @Column
    private Long community_ID;

    public CommunityID() {
    }

    public CommunityID(Long community_ID) {
        this.community_ID = community_ID;
    }

    public Long getCommunity_ID() {
        return community_ID;
    }

    public void setCommunity_ID(Long community_ID) {
        this.community_ID = community_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommunityID)) return false;
        CommunityID that = (CommunityID) o;
        return Objects.equals(community_ID, that.community_ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(community_ID);
    }

    @Override
    public String toString() {
        return "CommunityID{" +
                "community_ID=" + community_ID +
                '}';
    }
}
