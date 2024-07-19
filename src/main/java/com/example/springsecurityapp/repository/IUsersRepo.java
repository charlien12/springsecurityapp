package com.example.springsecurityapp.repository;

import com.example.springsecurityapp.models.UsersDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsersRepo extends JpaRepository<UsersDto,Integer> {
    Optional<UsersDto> findByUserName(String username);
}
