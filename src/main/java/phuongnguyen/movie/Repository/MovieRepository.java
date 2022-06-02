package phuongnguyen.movie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import phuongnguyen.movie.Table.Model.Movie;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query(value =
        "SELECT DISTINCT(m) FROM Movie m JOIN m.genres g ON (COALESCE(:genreId) IS NULL OR g.id IN(:genreId)) " +
            "WHERE " +
            "(:countryId IS NULL OR m.countryId = :countryId) AND " +
            "(:rate IS NULL OR m.rate >= :rate) AND " +
            "(:releaseDate IS NULL OR m.releaseDate >= :releaseDate)")
    List<Movie> search(@Param("genreId") List<Integer> genreId, @Param("countryId") String countryId,
                       @Param("rate") Float rate, @Param("releaseDate") LocalDateTime releaseDate);
}
