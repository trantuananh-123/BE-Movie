package phuongnguyen.movie.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuongnguyen.movie.Repository.UserRepository;
import phuongnguyen.movie.Repository.VerifyTokenRepository;
import phuongnguyen.movie.Table.Model.User;
import phuongnguyen.movie.Table.Response.GenerateResponse;
import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Table.Response.StatusCode;
import phuongnguyen.movie.Service.VerifyTokenService;
import phuongnguyen.movie.Table.DTO.UserDTO;
import phuongnguyen.movie.Table.Model.VerifyToken;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class VerifyTokenServiceImpl implements VerifyTokenService {

    @Autowired
    private VerifyTokenRepository verifyTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public VerifyToken createVerifyToken(User user) {
        Random random = new Random();
        VerifyToken verifyToken = null;
        if (userRepository.findByEmail(user.getEmail()) != null) {
            verifyToken = verifyTokenRepository.findByUserId(user.getId());
            if (verifyToken != null) {
                verifyTokenRepository.delete(verifyToken);
            }
        }
        verifyToken =
            new VerifyToken(String.valueOf(100000 + random.nextInt(900000)), LocalDateTime.now().plusMinutes(5), user);
        verifyTokenRepository.save(verifyToken);
        return verifyToken;
    }

    @Override
    public Response verifyToken(UserDTO dto) {
        VerifyToken verifyToken = verifyTokenRepository.findByUserUsername(dto.getUsername());
        if (verifyToken.getExpiredDate().isBefore(LocalDateTime.now())) {
            verifyTokenRepository.delete(verifyToken);
            return GenerateResponse.generateErrorResponse("VERIFY TOKEN IS EXPIRED", StatusCode.ERROR);
        }
        if (dto.getUsername().equals(verifyToken.getUser().getUsername()) &&
            verifyToken.getVerifyToken().equals(dto.getVerifyToken())) {
            User user = userRepository.findByUsername(dto.getUsername());
            user.setIsEnable(true);
            userRepository.save(user);
            verifyTokenRepository.delete(verifyToken);
            return GenerateResponse.generateSuccessResponse("VERIFY ACCOUNT SUCCESSFULLY", StatusCode.SUCCESS,
                user);
        }
        return GenerateResponse.generateErrorResponse("INVALID VERIFY TOKEN", StatusCode.ERROR);
    }
}
