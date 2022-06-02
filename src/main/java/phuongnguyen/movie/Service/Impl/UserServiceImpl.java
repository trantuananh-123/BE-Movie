package phuongnguyen.movie.Service.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import phuongnguyen.movie.Table.Model.Role;
import phuongnguyen.movie.Table.Model.User;
import phuongnguyen.movie.Table.Model.UserDetail;
import phuongnguyen.movie.Table.Model.VerifyToken;
import phuongnguyen.movie.Table.Response.*;
import phuongnguyen.movie.Repository.RoleRepository;
import phuongnguyen.movie.Repository.UserRepository;
import phuongnguyen.movie.Service.EmailService;
import phuongnguyen.movie.Service.UserService;
import phuongnguyen.movie.Service.VerifyTokenService;
import phuongnguyen.movie.Table.DTO.UserDTO;
import phuongnguyen.movie.Util.JwtUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private VerifyTokenService verifyTokenService;

    @Autowired
    private EmailService emailService;

    @Override
    public Response getAll() {
        return null;
    }

    @Override
    public Response signUp(UserDTO dto) {
        dto.setIsActive(true);
        dto.setIsDelete(false);
        dto.setIsEnable(false);
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            if (userRepository.existsByUsername(dto.getUsername())) {
                return GenerateResponse.generateErrorResponse("Username " + dto.getUsername() + " is already taken",
                    StatusCode.ERROR);
            }
            if (dto.getEmail() != null && userRepository.existsByEmail(dto.getEmail())) {
                return GenerateResponse.generateErrorResponse("Email " + dto.getEmail() + " is already taken",
                    StatusCode.ERROR);
            }

            Set<Role> roles = new HashSet<>();
            if (dto.getRoles() == null) {
                roles.add(roleRepository.findByName("ROLE_USER"));
            } else {
                for (Role i : dto.getRoles()) {
                    Optional<Role> optionalRole = roleRepository.findById(i.getId());
                    optionalRole.ifPresent(roles::add);
                }
            }

            User user = mapper.map(dto, User.class);
            user.setId(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRoles(roles);
            user.setCreatedDate(currentTime);

            userRepository.save(user);
            VerifyToken verifyToken = verifyTokenService.createVerifyToken(user);
            VerifyResponse verifyResponse =
                new VerifyResponse(user.getUsername(), user.getEmail(), verifyToken.getVerifyToken(),
                    verifyToken.getExpiredDate());
            emailService.sendEmail(user.getEmail(), user.getUsername(), verifyToken.getVerifyToken());
            return GenerateResponse.generateSuccessResponse("SUCCESS SIGN UP", StatusCode.SUCCESS, user);
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse("FAIL SIGN UP", StatusCode.ERROR);
        }
    }

    @Override
    public Response signIn(UserDTO dto) {
        SignInResponse response = new SignInResponse();

        if (!userRepository.findByUsernameOrEmail(dto.getUsername()).getIsEnable()) {
            return GenerateResponse.generateErrorResponse("VERIFY ACCOUNT TO CONTINUE", StatusCode.ERROR);
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);

        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        Set<Role> roleList = new LinkedHashSet<>();
        List<String> roleNameList = userDetail.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
        for (String i : roleNameList) {
            roleList.add(roleRepository.findByName(i));
        }

        User user = mapper.map(userDetail, User.class);
        user.setRoles(roleList);

        response.setAccessToken(token);
        response.setUser(user);
        response.setExpiredAt(jwtUtil.extractExpirationTime(token));
        return GenerateResponse.generateSuccessResponse("SUCCESS SIGN IN", StatusCode.SUCCESS, response);
    }


    @Override
    public Response getById(UserDTO dto) {
        return null;
    }

    @Override
    public Response delete(UserDTO dto) {
        return null;
    }

    @Override
    public Response existsByUsername(String username) {
        try {
            boolean isExisted = userRepository.existsByUsername(username);
            if (isExisted) {
                return GenerateResponse.generateSuccessResponse("Username is already taken", StatusCode.SUCCESS,
                    true);
            } else {
                return GenerateResponse.generateSuccessResponse("Username is available", StatusCode.SUCCESS,
                    false);
            }
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse("ERROR", StatusCode.ERROR);
        }
    }

    @Override
    public Response existsByEmail(String email) {
        try {
            boolean isExisted = userRepository.existsByEmail(email);
            if (isExisted) {
                return GenerateResponse.generateSuccessResponse("Email is already taken", StatusCode.SUCCESS,
                    true);
            } else {
                return GenerateResponse.generateSuccessResponse("Email is available", StatusCode.SUCCESS,
                    false);
            }
        } catch (Exception e) {
            logger.error(e);
            return GenerateResponse.generateErrorResponse("ERROR", StatusCode.ERROR);
        }
    }
}
