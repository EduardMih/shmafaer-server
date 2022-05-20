package com.licenta.shmafaerserver.repository;

import com.licenta.shmafaerserver.model.Rating;
import com.licenta.shmafaerserver.model.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {
}
*/


@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {

   @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.ratingId.project.id = :projectID")
   Optional<Double> getAverageProjectRating(@Param("projectID") Long projectID);

    //Rating save(Rating rating);

}

