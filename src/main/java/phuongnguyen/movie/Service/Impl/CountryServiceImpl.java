package phuongnguyen.movie.Service.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuongnguyen.movie.Table.DTO.CountryDTO;
import phuongnguyen.movie.Table.Model.Country;
import phuongnguyen.movie.Repository.CountryRepository;
import phuongnguyen.movie.Service.CountryService;
import phuongnguyen.movie.Table.Response.GenerateResponse;
import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Table.Response.StatusCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    Logger logger = LogManager.getLogger(CountryServiceImpl.class);

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Response getAll() {
        List<Country> countryList = countryRepository.findAll();
        return GenerateResponse.generateSuccessResponse("SUCCESS GET COUNTRY LIST", StatusCode.SUCCESS, countryList);
    }

    @Override
    public Response saveOrUpdate(CountryDTO dto) {
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            if (dto.getId() == null) {
                dto.setCreatedDate(currentTime);
            } else {
                Optional<Country> optionalCountry = countryRepository.findById(dto.getId());
                if (!optionalCountry.isPresent()) {
                    dto.setCreatedDate(currentTime);
                } else {
                    dto.setUpdatedDate(currentTime);
                }
            }
            Country country = mapper.map(dto, Country.class);
            countryRepository.save(country);
            return GenerateResponse.generateSuccessResponse("SUCCESS SAVE COUNTRY", StatusCode.SUCCESS, country);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }

    @Override
    public Response getById(CountryDTO dto) {
        try {
            if (dto.getId() == null) {
                return GenerateResponse.generateErrorResponse("Do not exist country with id: " + dto.getId(),
                    StatusCode.ERROR);
            }
            Optional<Country> optionalCountry = countryRepository.findById(dto.getId());
            if (optionalCountry.isPresent()) {
                Country country = optionalCountry.get();
                return GenerateResponse.generateSuccessResponse("SUCCESS FOUND COUNTRY", StatusCode.SUCCESS, country);
            }
            return GenerateResponse.generateErrorResponse("Do not exist country with id: ", StatusCode.ERROR);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }

    @Override
    public Response delete(CountryDTO dto) {
        try {
            if (dto.getId() == null) {
                return GenerateResponse.generateErrorResponse("Do not exist country with id: " + dto.getId(),
                    StatusCode.ERROR);
            }
            Optional<Country> optionalCountry = countryRepository.findById(dto.getId());
            if (optionalCountry.isPresent()) {
                countryRepository.deleteById(dto.getId());
                return GenerateResponse.generateSuccessResponse("SUCCESS DELETED", StatusCode.SUCCESS, null);
            }
            return GenerateResponse.generateErrorResponse("Do not exist country with id: ", StatusCode.ERROR);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }
}
