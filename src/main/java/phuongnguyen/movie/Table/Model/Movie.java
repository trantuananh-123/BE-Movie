package phuongnguyen.movie.Table.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "country_id")
    private String countryId;

    @Column(name = "title")
    private String title;

    @Column(name = "name")
    private String name;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @Column(name = "runtime")
    private Integer runtime;

    @Column(name = "overview")
    private String overview;

    @Column(name = "trailer_url")
    private String trailerUrl;

    @Column(name = "movie_url")
    private String movieUrl;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "age_restricted")
    private Integer ageRestricted;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "created_user_id")
    private String createdUserId;

    @Column(name = "updated_user_id")
    private String updatedUserId;

    @ManyToMany
    @JoinTable(name = "movie_genre",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;
}
