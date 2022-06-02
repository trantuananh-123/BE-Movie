package phuongnguyen.movie.Service;

import phuongnguyen.movie.Table.Model.User;
import phuongnguyen.movie.Table.Model.VerifyToken;
import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Table.DTO.UserDTO;

public interface VerifyTokenService {

    VerifyToken createVerifyToken(User user);

    Response verifyToken(UserDTO user);
}
