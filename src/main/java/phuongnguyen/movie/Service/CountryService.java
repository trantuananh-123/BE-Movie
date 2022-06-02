package phuongnguyen.movie.Service;

import phuongnguyen.movie.Table.DTO.CountryDTO;
import phuongnguyen.movie.Table.Response.Response;

public interface CountryService {
    Response getAll();

    Response saveOrUpdate(CountryDTO dto);

    Response getById(CountryDTO dto);

    Response delete(CountryDTO dto);
}
