package com.nobblecrafts.challenge.devsecopssr.dataaccess.movieevaluation.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.entity.UserActivityEntity;
import com.nobblecrafts.challenge.devsecopssr.domain.core.entity.MovieStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tmdb_me")
public class MovieEvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mve", nullable = false)
    @Min(0)
    @Max(1000000000)
    private Long movieId;


    @Enumerated(EnumType.STRING)
    @Column(name = "sts", columnDefinition = "VARCHAR(20)")
    private MovieStatus status;

    @JsonManagedReference("useractivity-evaluations")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private UserActivityEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEvaluationEntity movie = (MovieEvaluationEntity) o;
        return id != null && Objects.equals(id, movie.id);
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
        return "MovieEvaluationEntity{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", status=" + status +
                ", user=" + user.getId() +
                '}';
    }


}
