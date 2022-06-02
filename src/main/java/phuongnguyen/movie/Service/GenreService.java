package phuongnguyen.movie.Service;

import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Table.DTO.GenreDTO;

public interface GenreService {
    Response getAll();

    Response saveOrUpdate(GenreDTO dto);

    Response getById(GenreDTO dto);

    Response delete(GenreDTO dto);
}
