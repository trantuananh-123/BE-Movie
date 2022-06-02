package phuongnguyen.movie.Service.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuongnguyen.movie.Repository.GenreRepository;
import phuongnguyen.movie.Service.GenreService;
import phuongnguyen.movie.Table.DTO.GenreDTO;
import phuongnguyen.movie.Table.Model.Genre;
import phuongnguyen.movie.Table.Response.GenerateResponse;
import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Table.Response.StatusCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class GenreServiceImpl implements GenreService {

    Logger logger = LogManager.getLogger(GenreServiceImpl.class);

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Response getAll() {
        List<Genre> genreList = genreRepository.findAll();
        return GenerateResponse.generateSuccessResponse("SUCCESS GET GENRE LIST", StatusCode.SUCCESS, genreList);
    }

    @Override
    public Response saveOrUpdate(GenreDTO dto) {
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            if (dto.getId() == null) {
                dto.setCreatedDate(currentTime);
            } else {
                dto.setCreatedDate(dto.getCreatedDate() != null ? dto.getCreatedDate() : currentTime);
                dto.setUpdatedDate(currentTime);
            }
            Genre genre = mapper.map(dto, Genre.class);
            genreRepository.save(genre);
            return GenerateResponse.generateSuccessResponse("SUCCESS SAVE MOVIE", StatusCode.SUCCESS, genre);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }

    @Override
    public Response getById(GenreDTO dto) {
        try {
            if (dto.getId() == null) {
                return GenerateResponse.generateErrorResponse("Do not exist genre with id: " + dto.getId(),
                        StatusCode.ERROR);
            }
            Optional<Genre> optionalGenre = genreRepository.findById(dto.getId());
            if (optionalGenre.isPresent()) {
                Genre genre = optionalGenre.get();
                return GenerateResponse.generateSuccessResponse("SUCCESS FOUND GENRE", StatusCode.SUCCESS, genre);
            }
            return GenerateResponse.generateErrorResponse("Do not exist genre with id: ", StatusCode.ERROR);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }

    @Override
    public Response delete(GenreDTO dto) {
        try {
            if (dto.getId() == null) {
                return GenerateResponse.generateErrorResponse("Do not exist genre with id: " + dto.getId(),
                        StatusCode.ERROR);
            }
            Optional<Genre> optionalGenre = genreRepository.findById(dto.getId());
            if (optionalGenre.isPresent()) {
                genreRepository.deleteById(dto.getId());
                return GenerateResponse.generateSuccessResponse("SUCCESS DELETED", StatusCode.SUCCESS, null);
            }
            return GenerateResponse.generateErrorResponse("Do not exist genre with id: ", StatusCode.ERROR);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }
}
