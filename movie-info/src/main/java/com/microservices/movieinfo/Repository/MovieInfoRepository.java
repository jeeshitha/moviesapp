package com.microservices.movieinfo.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.microservices.movieinfo.model.Movie;

public interface MovieInfoRepository extends MongoRepository<Movie, Integer> {

	
	public List<Movie> findByReleaseYear(int releaseYear);
	
	public List<Movie> findByCategory(String category);
}
