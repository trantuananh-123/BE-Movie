package phuongnguyen.movie.Table.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import phuongnguyen.movie.Table.Model.Genre;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MovieDTO {

    private Integer id;
    private String countryId;
    private String title;
    private String name;
    private Float rate;
    private LocalDateTime releaseDate;
    private Integer runtime;
    private String overview;
    private String trailerUrl;
    private String movieUrl;
    private String posterUrl;
    private Integer ageRestricted;
    private Boolean isActive;
    private Boolean isDelete;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdUserId;
    private String updatedUserId;
    private List<Genre> genres;
    private List<Integer> genreId;
}
