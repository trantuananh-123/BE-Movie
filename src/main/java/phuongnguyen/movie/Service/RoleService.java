package phuongnguyen.movie.Service;

import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Table.DTO.RoleDTO;

public interface RoleService {
    Response getAll();

    Response saveOrUpdate(RoleDTO dto);

    Response getById(RoleDTO dto);

    Response delete(RoleDTO dto);
}
