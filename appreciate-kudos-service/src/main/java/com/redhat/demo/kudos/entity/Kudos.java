package com.redhat.demo.kudos.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Kudos implements Serializable {

    @Id
    private Long id;
    private String userFrom;
    private String userTo;
    private String description;
    private Date creationDate;

    public Kudos() {
    }

    public Kudos(Long id, String userFrom, String userTo, String description, Date creationDate, int likes) {
        this.id = id;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getUserFrom() {
        return userFrom;
    }

    private void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    private void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    private void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Kudos {" +
                "id=" + id +
                ", userFrom='" + userFrom + '\'' +
                ", userTo='" + userTo + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Kudos kudos = new Kudos();

        public Builder id(Long id) {
            kudos.setId(id);
            return this;
        }

        public Builder userFrom(String userFrom) {
            kudos.setUserFrom(userFrom);
            return this;
        }

        public Builder userTo(String userTo) {
            kudos.setUserTo(userTo);
            return this;
        }

        public Builder description(String description) {
            kudos.setDescription(description);
            return this;
        }

        public Builder creationDate(Date creationDate) {
            kudos.setCreationDate(creationDate);
            return this;
        }

        public Kudos build() {
            return kudos;
        }

    }


}
