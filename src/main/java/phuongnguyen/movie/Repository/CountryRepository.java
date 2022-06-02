package phuongnguyen.movie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phuongnguyen.movie.Table.Model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
}
