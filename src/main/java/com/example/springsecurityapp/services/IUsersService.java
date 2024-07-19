package com.example.springsecurityapp.services;

import com.example.springsecurityapp.models.UsersDto;
import com.example.springsecurityapp.response.LoginDtoResponse;
import com.example.springsecurityapp.response.RegisterResponse;

public interface IUsersService {
    RegisterResponse Register(UsersDto usersDto);
    LoginDtoResponse LOGIN_DTO_RESPONSE(String username, String password);
}
