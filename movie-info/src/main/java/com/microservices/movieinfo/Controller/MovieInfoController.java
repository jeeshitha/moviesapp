package com.microservices.movieinfo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.movieinfo.Repository.MovieInfoRepository;
import com.microservices.movieinfo.model.Movie;

@RestController
@RequestMapping("/api")
public class MovieInfoController {
	
	@Autowired
	private MovieInfoRepository movieInfoRepository;
	
	@GetMapping("/movie/{movieId}")
	public ResponseEntity<Movie> getMovieDetails(@PathVariable int movieId){
		System.out.println("movieid "+ movieId);
		Optional<Movie> result= movieInfoRepository.findById(movieId);
		ResponseEntity<Movie> response=null;
		System.out.println("result "+result);
		if(result.isPresent())
			
			response=new ResponseEntity<Movie>(result.get(),HttpStatus.OK);
		else 
			response=new ResponseEntity<Movie>(result.get(),HttpStatus.NOT_FOUND);
	
		return response;
	}
	
	@GetMapping("/movies/{category}")
	public HttpEntity<List<Movie>> getMovieDetailsByCategory(@PathVariable String category){
		List<Movie> movies= movieInfoRepository.findByCategory(category);
		System.out.println("movies list"+movies);
		return new HttpEntity<List<Movie>>(movies);
	}
	
	@GetMapping("/movieByYear/{releaseYear}")
	public HttpEntity<List<Movie>> getMovieDetailsByReleaseYear(@PathVariable int releaseYear){
		List<Movie> movies= movieInfoRepository.findByReleaseYear(releaseYear);
		System.out.println("movies list"+movies);
		return new HttpEntity<List<Movie>>(movies);
	}

}
