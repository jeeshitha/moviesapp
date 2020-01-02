package com.microservices.moviecatalog.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.moviecatalog.model.CatalogItem;
import com.microservices.moviecatalog.model.Movie;
import com.microservices.moviecatalog.model.MovieCatalog;
import com.microservices.moviecatalog.model.MovieRating;
import com.microservices.moviecatalog.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/api")
public class MovieCatalogController {
	
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/catalog/{userId}")
	@HystrixCommand(fallbackMethod = "getMovieCatalogFallback")
	ResponseEntity<MovieCatalog> getMovieCatalog(@PathVariable String userId) {
		System.out.println("catalog enterd"+userId);
		
		UserRating userRating = getUserRatings(userId);
		
		List<MovieRating> movieRatings=userRating.getMovieRatings();
		System.out.println("movieRatings enterd"+movieRatings);

		List<CatalogItem> catalogItems = new ArrayList<CatalogItem>();
		
		for(MovieRating movieRating : movieRatings) {
			Movie movie = getMovieDetails(movieRating);
			System.out.println("movie enterd"+movie);
			catalogItems.add(new CatalogItem(movie, movieRating.getRating()));
		}
		MovieCatalog movieCatalog =new MovieCatalog(catalogItems);
		
		return new ResponseEntity<MovieCatalog>(movieCatalog,HttpStatus.OK);
	}
	@HystrixCommand(fallbackMethod = "getMovieDetailsFallback")
	public Movie getMovieDetails(MovieRating movieRating) {
		Movie movie = restTemplate.getForObject("http://MOVIE-INFO/api/movie/"+movieRating.getMovieId(), Movie.class);
		return movie;
	}
	
	public Movie getMovieDetailsFallback(MovieRating movieRating) {
		return new Movie(0, "movie info not found", 0, "Not Found", null); 
	}

	@HystrixCommand(fallbackMethod ="getUserRatingsFallback")
	public UserRating getUserRatings(String userId) {
		UserRating userRating = restTemplate.getForObject("http://MOVIE-RATING/api/ratings/"+userId, UserRating.class);
		return userRating;
	}
	
	public UserRating getUserRatingsFallback(String userId) {
	
		UserRating userRating = new UserRating(userId, Arrays.asList(new MovieRating(0, 0)));
		return userRating;
	}
	
	@GetMapping("/catalog/ratedMovieCount/{userId}")
	public ResponseEntity<Integer> getTotalNumberOfMoviesRated(@PathVariable String userId) {
		
		int totalNumOfMoviesRated=0;
		
		totalNumOfMoviesRated=
				restTemplate.getForObject("http://MOVIE-RATING/api/ratedMovieCount/"+userId, Integer.class);
		return new ResponseEntity<Integer>(totalNumOfMoviesRated, HttpStatus.OK);		
		
	}

}
