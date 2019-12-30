package com.microservices.movierating.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="userratings")
public class UserRating {

	@Id
	private String userId;
	private List<MovieRating> movieRatings;
	
	public UserRating() {
		super();
	}

	public UserRating(String userId, List<MovieRating> movieRatings) {
		super();
		this.userId = userId;
		this.movieRatings = movieRatings;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<MovieRating> getMovieRatings() {
		return movieRatings;
	}

	public void setMovieRatings(List<MovieRating> movieRatings) {
		this.movieRatings = movieRatings;
	}
	
}
