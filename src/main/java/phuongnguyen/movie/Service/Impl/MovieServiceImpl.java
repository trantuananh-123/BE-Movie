package phuongnguyen.movie.Service.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuongnguyen.movie.Repository.GenreRepository;
import phuongnguyen.movie.Repository.MovieRepository;
import phuongnguyen.movie.Table.DTO.GenreDTO;
import phuongnguyen.movie.Table.DTO.MovieDTO;
import phuongnguyen.movie.Table.Model.Genre;
import phuongnguyen.movie.Table.Response.GenerateResponse;
import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Table.Response.StatusCode;
import phuongnguyen.movie.Service.MovieService;
import phuongnguyen.movie.Table.Model.Movie;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MovieServiceImpl implements MovieService {

    Logger logger = LogManager.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Response getAll() {
        List<Movie> movieList = movieRepository.findAll();
        return GenerateResponse.generateSuccessResponse("SUCCESS GET MOVIE LIST", StatusCode.SUCCESS, movieList);
    }

    @Override
    public Response saveOrUpdate(MovieDTO dto) {
        List<Genre> genres = new ArrayList<>();
        if (dto.getGenreId() != null) {
            for (Integer genreId : dto.getGenreId()) {
                Optional<Genre> optionalGenre = genreRepository.findById(genreId);
                optionalGenre.ifPresent(genres::add);
            }
        }
        dto.setGenres(genres);
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            if (dto.getId() == null) {
                dto.setCreatedDate(currentTime);
            } else {
                dto.setUpdatedDate(currentTime);
            }
            Movie movie = mapper.map(dto, Movie.class);
            movieRepository.save(movie);
            return GenerateResponse.generateSuccessResponse("SUCCESS SAVE MOVIE", StatusCode.SUCCESS, movie);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }

    @Override
    public Response getById(MovieDTO dto) {
        try {
            if (dto.getId() == null) {
                return GenerateResponse.generateErrorResponse("Do not exist movie with id: " + dto.getId(),
                    StatusCode.ERROR);
            }
            Optional<Movie> optionalMovie = movieRepository.findById(dto.getId());
            if (optionalMovie.isPresent()) {
                Movie movie = optionalMovie.get();
                return GenerateResponse.generateSuccessResponse("SUCCESS FOUND MOVIE", StatusCode.SUCCESS, movie);
            }
            return GenerateResponse.generateErrorResponse("Do not exist movie with id: ", StatusCode.ERROR);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }

    @Override
    public Response delete(MovieDTO dto) {
        try {
            if (dto.getId() == null) {
                return GenerateResponse.generateErrorResponse("Do not exist movie with id: " + dto.getId(),
                    StatusCode.ERROR);
            }
            Optional<Movie> optionalMovie = movieRepository.findById(dto.getId());
            if (optionalMovie.isPresent()) {
                Movie movie = optionalMovie.get();
                movie.setIsActive(false);
                movie.setIsDelete(true);
                movieRepository.save(movie);
                return GenerateResponse.generateSuccessResponse("SUCCESS DELETED", StatusCode.SUCCESS, movie);
            }
            return GenerateResponse.generateErrorResponse("Do not exist movie with id: ", StatusCode.ERROR);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }

    @Override
    public Response getMovieByGenre(GenreDTO dto) {
        Genre genre = mapper.map(dto, Genre.class);
        List<Movie> movieList = movieRepository.findAll();
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movieList) {
            List<Genre> genreList = movie.getGenres();
            genreList.forEach(g -> {
                if (Boolean.TRUE.equals(g.getId().intValue() == genre.getId().intValue() && g.getIsActive()) &&
                    Boolean.FALSE.equals(g.getIsDelete())) {
                    result.add(movie);
                }
            });
        }
        if (result.isEmpty()) {
            return GenerateResponse.generateSuccessResponse("NO MOVIE WITH THIS GENRE", StatusCode.SUCCESS, result);
        }
        return GenerateResponse.generateSuccessResponse("SUCCESS GET MOVIE LIST BY GENRE", StatusCode.SUCCESS, result);
    }

    @Override
    public Response search(MovieDTO dto) {
        List<Movie> list =
            movieRepository.search(dto.getGenreId(), dto.getCountryId(), dto.getRate(), dto.getReleaseDate());
        return GenerateResponse.generateSuccessResponse("SUCCESS SEARCH MOVIE", StatusCode.SUCCESS, list);
    }
}
