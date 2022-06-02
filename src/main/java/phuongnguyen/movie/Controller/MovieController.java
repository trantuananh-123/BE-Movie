package phuongnguyen.movie.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import phuongnguyen.movie.Table.DTO.GenreDTO;
import phuongnguyen.movie.Table.DTO.MovieDTO;
import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Service.MovieService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/get-all")
    public Response getAll() {
        return movieService.getAll();
    }

    @PostMapping(value = "/save")
    public Response saveOrUpdate(@RequestBody MovieDTO dto) {
        return movieService.saveOrUpdate(dto);
    }

    @PostMapping(value = "/get-by-id")
    public Response getById(@RequestBody MovieDTO dto) {
        return movieService.getById(dto);
    }

    @PostMapping(value = "/delete")
    public Response delete(@RequestBody MovieDTO dto) {
        return movieService.delete(dto);
    }

    @PostMapping(value = "/get-by-genre")
    public Response getByGenre(@RequestBody GenreDTO dto) {
        return movieService.getMovieByGenre(dto);
    }

    @PostMapping(value = "/search")
    public Response search(@RequestBody MovieDTO dto) {
        return movieService.search(dto);
    }

}
