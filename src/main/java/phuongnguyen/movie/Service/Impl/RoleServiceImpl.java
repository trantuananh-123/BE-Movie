package phuongnguyen.movie.Service.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuongnguyen.movie.Repository.RoleRepository;
import phuongnguyen.movie.Table.Model.Role;
import phuongnguyen.movie.Table.Response.GenerateResponse;
import phuongnguyen.movie.Table.Response.Response;
import phuongnguyen.movie.Table.Response.StatusCode;
import phuongnguyen.movie.Service.RoleService;
import phuongnguyen.movie.Table.DTO.RoleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private static final String ROLE_ID_NOT_EXIST = "Do not exist role with id: ";

    Logger logger = LogManager.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Response getAll() {
        List<Role> roleList = roleRepository.findAll();
        return GenerateResponse.generateSuccessResponse("SUCCESS GET ROLE LIST", StatusCode.SUCCESS, roleList);
    }

    @Override
    public Response saveOrUpdate(RoleDTO dto) {
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            if (dto.getId() == null) {
                dto.setCreatedDate(currentTime);
            } else {
                dto.setUpdatedDate(currentTime);
            }
            Role role = mapper.map(dto, Role.class);
            roleRepository.save(role);
            return GenerateResponse.generateSuccessResponse("SUCCESS SAVE ROLE", StatusCode.SUCCESS, role);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }

    @Override
    public Response getById(RoleDTO dto) {
        try {
            if (dto.getId() == null) {
                return GenerateResponse.generateErrorResponse(ROLE_ID_NOT_EXIST + dto.getId(),
                        StatusCode.ERROR);
            }
            Optional<Role> optionalRole = roleRepository.findById(dto.getId());
            if (optionalRole.isPresent()) {
                Role role = optionalRole.get();
                return GenerateResponse.generateSuccessResponse("SUCCESS FOUND ROLE", StatusCode.SUCCESS, role);
            }
            return GenerateResponse.generateErrorResponse(ROLE_ID_NOT_EXIST, StatusCode.ERROR);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }

    @Override
    public Response delete(RoleDTO dto) {
        try {
            if (dto.getId() == null) {
                return GenerateResponse.generateErrorResponse(ROLE_ID_NOT_EXIST + dto.getId(),
                        StatusCode.ERROR);
            }
            Optional<Role> optionalRole = roleRepository.findById(dto.getId());
            if (optionalRole.isPresent()) {
                Role role = optionalRole.get();
                role.setIsActive(false);
                role.setIsDelete(true);
                roleRepository.save(role);
                return GenerateResponse.generateSuccessResponse("SUCCESS DELETED", StatusCode.SUCCESS, role);
            }
            return GenerateResponse.generateErrorResponse(ROLE_ID_NOT_EXIST, StatusCode.ERROR);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse(e.getLocalizedMessage(), StatusCode.ERROR);
        }
    }
}
