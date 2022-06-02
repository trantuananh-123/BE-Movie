package phuongnguyen.movie.Service;

import phuongnguyen.movie.Table.DTO.GenreDTO;
import phuongnguyen.movie.Table.DTO.MovieDTO;
import phuongnguyen.movie.Table.Model.Movie;
import phuongnguyen.movie.Table.Response.Response;

public interface MovieService {
    Response getAll();

    Response saveOrUpdate(MovieDTO dto);

    Response getById(MovieDTO dto);

    Response delete(MovieDTO dto);

    Response getMovieByGenre(GenreDTO dto);

    Response search(MovieDTO dto);
}
