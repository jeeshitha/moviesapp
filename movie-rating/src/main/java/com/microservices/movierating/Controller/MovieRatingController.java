package com.microservices.movierating.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.movierating.Repository.UserRatingRepository;
import com.microservices.movierating.model.MovieRating;
import com.microservices.movierating.model.UserRating;

@RestController
@RequestMapping("/api")


public class MovieRatingController {
	
	@Autowired
	private UserRatingRepository userRatingRepository;

	
	@GetMapping("/ratings/{userId}")
	public ResponseEntity<UserRating> getAllUserRatings(@PathVariable String userId) {
		
		Optional<UserRating> result = userRatingRepository.findById(userId);
		ResponseEntity<UserRating> response = null;
		System.out.println("result "+result);
		if (result.isPresent()) 
			response = new ResponseEntity<UserRating>(result.get(), HttpStatus.OK);
		else
			response = new ResponseEntity<UserRating>(HttpStatus.NOT_FOUND);

		return response;
	}
	
	@GetMapping("/ratedMovieCount/{userId}")
	public ResponseEntity<Integer> getTotalNumberOfMoviesRated(@PathVariable String userId) {
		
		Optional<UserRating> result = userRatingRepository.findById(userId);
		ResponseEntity<Integer> response = null;
		System.out.println("result "+result);
		if (result.isPresent()) 
			response = new ResponseEntity<Integer>(result.get().getMovieRatings().size(), HttpStatus.OK);
		else
			response = new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);

		return response;
	}
}
