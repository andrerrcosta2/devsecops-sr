package com.nobblecrafts.challenge.devsecopssr.app.mvc;

import com.nobblecrafts.challenge.devsecopssr.domain.MovieService;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "home/movies")
@RequiredArgsConstructor
public class MovieController {

    public static final String PATH = "/home/movies";
    private final MovieService movieService;

    @GetMapping
    public String getMoviesList(Model model) {
        Page<TMDBMovie> movies = movieService.listMovies(1);
        movies.forEach(movie -> {
            log.info("Movie {}: {}", movie.id(), movie);
        });
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("{movieId}")
    public String getMovieDetails(@PathVariable(name = "movieId", required = true) Long movieId, Model model) {
        MovieDetailsWithEvaluation movieDetails = movieService.getMovieDetails(movieId);
        model.addAttribute("details", movieDetails);
        return "movie-details";
    }
}
