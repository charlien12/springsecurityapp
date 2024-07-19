package com.example.springsecurityapp.controller;

import com.example.springsecurityapp.models.UsersDto;
import com.example.springsecurityapp.response.LoginDtoResponse;
import com.example.springsecurityapp.response.RegisterResponse;
import com.example.springsecurityapp.services.IUsersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/users/")
@RequiredArgsConstructor
public class UsersController {
    private final IUsersService iUsersService;
    @PostMapping("register")
    public ResponseEntity<?> Register(@RequestBody UsersDto usersDto){
        try{
            RegisterResponse register = iUsersService.Register(usersDto);
            if(register.getHttpStatus().equals(HttpStatus.CREATED)){
                return new ResponseEntity<>(register,HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(register,HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("login")
    public ResponseEntity<?> Login(@RequestBody UsersDto usersDto){
        try{
            LoginDtoResponse loginDtoResponse = iUsersService.LOGIN_DTO_RESPONSE(usersDto.getUserName(), usersDto.getPassword());
            if(loginDtoResponse.getHttpStatus().equals(HttpStatus.OK)){
                return new ResponseEntity<>(loginDtoResponse,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(loginDtoResponse,HttpStatus.FORBIDDEN);
            }
        }
        catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
