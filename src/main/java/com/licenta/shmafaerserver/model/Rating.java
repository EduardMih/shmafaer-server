package com.licenta.shmafaerserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@IdClass(RatingId.class)
public class Rating implements Serializable {

    @EmbeddedId
    private RatingId ratingId;

/*
    @Id
    @ManyToOne
    private AppUser user;

    @Id
    @ManyToOne
    private Project project;
*/
    @NotNull
    @Range(min = 1, max = 10, message = "Rating should be between 1 and 10")
    private Integer rating;


}
