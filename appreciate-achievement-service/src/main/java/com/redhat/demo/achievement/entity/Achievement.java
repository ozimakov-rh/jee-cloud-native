package com.redhat.demo.achievement.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Achievement implements Serializable {

    @Id
    private Long id;
    private String owner;
    private String type;
    private Date creationDate;

    public Achievement() {
    }

    public Achievement(Long id, String owner, String type, Date creationDate) {
        this.id = id;
        this.owner = owner;
        this.type = type;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    private void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    private void setType(String description) {
        this.type = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    private void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", type='" + type + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Achievement achievement = new Achievement();

        public Builder id(Long id) {
            achievement.setId(id);
            return this;
        }

        public Builder owner(String owner) {
            achievement.setOwner(owner);
            return this;
        }

        public Builder type(String type) {
            achievement.setType(type);
            return this;
        }

        public Builder creationDate(Date creationDate) {
            achievement.setCreationDate(creationDate);
            return this;
        }

        public Achievement build() {
            return achievement;
        }

    }


}
