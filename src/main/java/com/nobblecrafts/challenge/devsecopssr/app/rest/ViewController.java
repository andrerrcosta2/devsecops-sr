package com.nobblecrafts.challenge.devsecopssr.app.rest;

import com.nobblecrafts.challenge.devsecopssr.domain.MovieService;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.LoginRequest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.RegisterAccountRequest;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovie;
import com.nobblecrafts.challenge.devsecopssr.security.context.DomainSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class ViewController {

    private final MovieService movieService;
    private final DomainSecurityContext securityContext;

    @GetMapping
    public String getDefaultIndex() {
        return securityContext.isAuthenticated() ? "redirect:/home" : "redirect:/login";
    }

    @GetMapping("home")
    public String getDefaultHome() {
        return "redirect:/home/movies";
    }

    @GetMapping("login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", LoginRequest.builder().build());
        return "login";
    }

    @GetMapping("register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerAccountRequest", RegisterAccountRequest.builder().build());
        return "register";
    }

    @GetMapping("home/movies")
    public String getMoviesList(Model model) {

        Page<TMDBMovie> movies = movieService.listMovies(1);
        movies.forEach(movie -> {
            log.info("Movie {}: {}", movie.id(), movie);
        });

        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("home/movies/{movieId}")
    public String getMovieDetails(@PathVariable(name = "movieId", required = true) Long movieId, Model model) {
        MovieDetailsWithEvaluation movieDetails = movieService.getMovieDetails(movieId);
        model.addAttribute("details", movieDetails);
        return "movie-details";
    }

}
