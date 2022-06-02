package phuongnguyen.movie.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Service.RoleService;
import phuongnguyen.movie.Table.DTO.RoleDTO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/get-all")
    public Response getAll() {
        return roleService.getAll();
    }

    @PostMapping(value = "/save")
    public Response saveOrUpdate(@RequestBody RoleDTO dto) {
        return roleService.saveOrUpdate(dto);
    }

    @PostMapping(value = "/get-by-id")
    public Response getById(@RequestBody RoleDTO dto) {
        return roleService.getById(dto);
    }

    @PostMapping(value = "/delete")
    public Response delete(@RequestBody RoleDTO dto) {
        return roleService.delete(dto);
    }
}
