package phuongnguyen.movie.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuongnguyen.movie.Repository.UserRepository;
import phuongnguyen.movie.Table.Model.User;
import phuongnguyen.movie.Table.Model.VerifyToken;
import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Service.EmailService;
import phuongnguyen.movie.Service.VerifyTokenService;
import phuongnguyen.movie.Table.DTO.UserDTO;
import phuongnguyen.movie.Table.Response.VerifyResponse;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/v1/verify")
public class VerifyUserController {

    @Autowired
    private VerifyTokenService verifyTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public Response verify(@RequestBody UserDTO dto) {
        return verifyTokenService.verifyToken(dto);
    }

    @PostMapping(value = "/send-token")
    public VerifyToken sendVerifyToken(@RequestBody UserDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        VerifyToken verifyToken = verifyTokenService.createVerifyToken(user);
        VerifyResponse verifyResponse =
            new VerifyResponse(user.getUsername(), user.getEmail(), verifyToken.getVerifyToken(),
                verifyToken.getExpiredDate());
//        emailService.sendEmail(user.getEmail(), user.getUsername(), verifyToken.getVerifyToken());
        return verifyToken;
    }
}
