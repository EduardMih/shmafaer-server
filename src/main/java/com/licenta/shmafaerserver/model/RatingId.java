package com.licenta.shmafaerserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class RatingId implements Serializable {
    @ManyToOne
    private AppUser user;
    @ManyToOne
    private Project project;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingId ratingId = (RatingId) o;
        return Objects.equals(user, ratingId.user) && Objects.equals(project, ratingId.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, project);
    }
}

