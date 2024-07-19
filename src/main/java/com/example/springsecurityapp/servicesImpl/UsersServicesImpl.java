package com.example.springsecurityapp.servicesImpl;

import com.example.springsecurityapp.enums.Role;
import com.example.springsecurityapp.models.UsersDto;
import com.example.springsecurityapp.repository.IUsersRepo;
import com.example.springsecurityapp.response.LoginDtoResponse;
import com.example.springsecurityapp.response.RegisterResponse;
import com.example.springsecurityapp.services.IUsersService;
import com.example.springsecurityapp.utils.JwtUtil;
import com.example.springsecurityapp.utils.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServicesImpl implements IUsersService {
    private final IUsersRepo usersRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    @Override
    public RegisterResponse Register(UsersDto usersDto) {
        Optional<UsersDto> byUserName = usersRepo.findByUserName(usersDto.getUserName());
        if(byUserName.isPresent()){
            return new RegisterResponse("User is already exist", HttpStatus.BAD_REQUEST);
        }else{
            usersDto.setRole(Role.USER);
            usersDto.setPassword(passwordEncoder.encode(usersDto.getPassword()));
            usersRepo.save(usersDto);
            return new RegisterResponse("User is successfully created",HttpStatus.CREATED);

        }
    }

    @Override
    public LoginDtoResponse LOGIN_DTO_RESPONSE(String username, String password) {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = userService.loadUserByUsername(username);
        String token = jwtUtil.generateToken(userDetails);
        UsersDto byUserName = usersRepo.findByUserName(username).orElseThrow(()->new RuntimeException("User is not exist"));
        boolean matches = passwordEncoder.matches(password, byUserName.getPassword());
        if(byUserName.getUserName().equals(username)&&(matches)){
            return new LoginDtoResponse("Login successfully",HttpStatus.OK,token);
        }else{
            return new LoginDtoResponse("Invalid credentials",HttpStatus.FORBIDDEN,null);
        }

    }
}
