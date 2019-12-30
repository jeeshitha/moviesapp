package com.microservices.moviecatalog.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.moviecatalog.model.CatalogItem;
import com.microservices.moviecatalog.model.Movie;
import com.microservices.moviecatalog.model.MovieCatalog;
import com.microservices.moviecatalog.model.MovieRating;
import com.microservices.moviecatalog.model.UserRating;

@RestController
@RequestMapping("/api")
public class MovieCatalogController {
	
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/catalog/{userId}")
	ResponseEntity<MovieCatalog> getMovieCatlog(@PathVariable String userId) {
		System.out.println("catlog enterd"+userId);
		UserRating userRating = restTemplate.getForObject("http://MOVIE-RATING/api/ratings/"+userId, UserRating.class);
		List<MovieRating> movieRatings=userRating.getMovieRatings();
		System.out.println("movieRatings enterd"+movieRatings);

		List<CatalogItem> catalogItems = new ArrayList<CatalogItem>();
		
		for(MovieRating movieRating : movieRatings) {
			Movie movie = restTemplate.getForObject("http://MOVIE-INFO/api/movie/"+movieRating.getMovieId(), Movie.class);
			System.out.println("movie enterd"+movie);
			catalogItems.add(new CatalogItem(movie, movieRating.getRating()));
		}
		MovieCatalog movieCatalog =new MovieCatalog(catalogItems);
		
		return new ResponseEntity<MovieCatalog>(movieCatalog,HttpStatus.OK);
	}

	

}
