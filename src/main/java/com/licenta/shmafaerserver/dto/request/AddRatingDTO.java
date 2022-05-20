package com.licenta.shmafaerserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class AddRatingDTO {

    private Long projectID;

    @Range(min = 1, max = 10, message = "Rating should be between 1 and 10")
    private Integer ratingValue;
}
