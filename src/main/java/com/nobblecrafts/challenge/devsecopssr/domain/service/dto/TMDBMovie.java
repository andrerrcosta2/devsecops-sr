package com.nobblecrafts.challenge.devsecopssr.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
public record TMDBMovie(
        int id,
        boolean adult,
        @JsonProperty("backdrop_path") String backdropPath,
        @JsonProperty("genre_ids") List<Long> genreIds,
        @JsonProperty("original_language") String originalLanguage,
        @JsonProperty("original_title") String originalTitle,
        String overview,
        float popularity,
        @JsonProperty("poster_path") String posterPath,
        @JsonProperty("release_date") Date releaseDate,
        String title,
        boolean video,
        @JsonProperty("vote_average") float voteAverage,
        @JsonProperty("vote_count") long voteCount
) {}

//Brasil2025$
//
//
//    @Bean
//    public FilterRegistrationBean<DebugFilter> debugFilterRegistration(DebugFilter filter) {
//        FilterRegistrationBean<DebugFilter> registrationBean = new FilterRegistrationBean<>(filter);
//        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
//        return registrationBean;
//    }
//
//    @Bean
//    public DebugFilter debugFilter() {
//        return new DebugFilter();
//    }
//
//    class DebugFilter implements Filter {
//
//        @Override
//        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
//            var request = (HttpServletRequest) servletRequest;
//
//            Enumeration<String> headers = request.getHeaders(HttpHeaders.AUTHORIZATION);
//
//            if (headers != null && headers.hasMoreElements()) {
//                System.out.printf("\n\nDebug Filter: Headers not null - %s\n\n", headers.hasMoreElements());
//                headers.asIterator().forEachRemaining(System.out::println);
//                while (headers.hasMoreElements()) {
//                    String authorizationHeader = headers.nextElement();
//                    System.out.printf("\n\nDebug Filter: Authorization header - %s\n\n", authorizationHeader);
//                }
//            } else {
//                System.out.println("\n\nDebug Filter: No Authorization header found.\n\n");
//            }
//            System.out.println("\n\nDebug Filter: End.\n\n");
//            filterChain.doFilter(request, servletResponse);
//        }
//    }