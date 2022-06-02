package phuongnguyen.movie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuongnguyen.movie.Table.Model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
