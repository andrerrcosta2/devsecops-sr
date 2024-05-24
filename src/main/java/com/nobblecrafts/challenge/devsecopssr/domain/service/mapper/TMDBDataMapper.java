package com.nobblecrafts.challenge.devsecopssr.domain.service.mapper;

import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBMovie;
import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.TMDBPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TMDBDataMapper {

    public Page<TMDBMovie> toPageable(TMDBPage<TMDBMovie> tmdbPage) {
        Pageable pageable = PageRequest.of(tmdbPage.page() - 1, tmdbPage.results().size());
        return new PageImpl<>(
                new ArrayList<>(tmdbPage.results()),
                pageable,
                tmdbPage.totalResults()
        );
    }
}
