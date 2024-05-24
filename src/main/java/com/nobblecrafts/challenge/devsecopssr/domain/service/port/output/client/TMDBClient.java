package com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.client;

import com.nobblecrafts.challenge.devsecopssr.config.FeignConfig;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovie;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovieDetails;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "tmdb-client",
        url = "${devsecopsapp.integration.api.tmdb}",
        configuration = FeignConfig.class)
public interface TMDBClient {

    @GetMapping("${devsecopsapp.integration.api.tmdb-movie-discover-path}")
    TMDBPage<TMDBMovie> getMovieList(@RequestParam("page") int page);

    @GetMapping("${devsecopsapp.integration.api.tmdb-movie-details-path}/{movie_id}")
    TMDBMovieDetails getMovieDetails(@PathVariable("movie_id") Long movieId);
}
