package com.microservices.movierating.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.microservices.movierating.model.UserRating;

public interface UserRatingRepository extends MongoRepository<UserRating, String> {

	
}
