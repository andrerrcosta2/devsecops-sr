package com.nobblecrafts.challenge.devsecopssr.domain;

import com.nobblecrafts.challenge.devsecopssr.domain.service.dto.MovieDetailsWithEvaluation;

public interface UserActivityService {

    // Ao pesquisar um filme o usuário poderá marcar o título com as seguintes opções, ASSISTIDO e/ou FAVORITOS, ou PRETENDE ASSISTIR,
    // além disso essas informações devem ser persistidas em um banco SQL (Mysql, Postgres, Sqlite, SQLServer, etc.);
    MovieDetailsWithEvaluation evaluateMovie(MovieDetailsWithEvaluation evaluation);

}
