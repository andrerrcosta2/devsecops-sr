package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovie;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovieDetails;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBPage;
import com.nobblecrafts.challenge.devsecopssr.domain.service.mapper.TMDBDataMapper;
import com.nobblecrafts.challenge.devsecopssr.domain.service.port.output.client.TMDBClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallTMDBHelper {

    private final TMDBClient tmdbClient;
    private final TMDBDataMapper tmdbDataMapper;

    public Page<TMDBMovie> listMovies(Integer page) {
        TMDBPage<TMDBMovie> list = tmdbClient.getMovieList(page);
        return tmdbDataMapper.toPageable(list);
    }

    public TMDBMovieDetails getMovieDetails(Long movieId) {
        return tmdbClient.getMovieDetails(movieId);
    }
}
