package com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.movieevaluation.entity.MovieEvaluationEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "uac")
public class UserActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @JsonBackReference("useractivity-evaluations")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<MovieEvaluationEntity> movieEvaluations = new HashSet<>();

    // ASSOCIATION METHODS
    public void addMovieEvaluation(MovieEvaluationEntity evaluation) {
        if (evaluation.getUser() != this) evaluation.setUser(this);
        movieEvaluations.add(evaluation);
    }

    public void removeEvaluation(MovieEvaluationEntity evaluation) {
        evaluation.setUser(null);
        movieEvaluations.remove(evaluation);
    }

    public void removeEvaluations() {
        for (MovieEvaluationEntity evaluation : this.movieEvaluations) {
            evaluation.setUser(null);
            movieEvaluations.remove(evaluation);
        }
    }

    // OVERRIDING

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserActivityEntity user = (UserActivityEntity) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? System.identityHashCode(this) : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "UserActivityEntity{" +
                "id=" + id +
                ", account=" + account +
                ", movieEvaluations=" + movieEvaluations +
                '}';
    }
}
