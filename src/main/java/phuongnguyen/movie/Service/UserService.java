package phuongnguyen.movie.Service;

import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Table.DTO.UserDTO;

public interface UserService {

    Response getAll();

    Response signUp(UserDTO dto);

    Response signIn(UserDTO dto);

    Response getById(UserDTO dto);

    Response delete(UserDTO dto);

    Response existsByUsername(String username);

    Response existsByEmail(String email);
}
