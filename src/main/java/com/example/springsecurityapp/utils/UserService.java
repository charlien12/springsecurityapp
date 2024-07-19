package com.example.springsecurityapp.utils;

import com.example.springsecurityapp.models.UsersDto;
import com.example.springsecurityapp.repository.IUsersRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final IUsersRepo usersRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersDto> byUserName = usersRepo.findByUserName(username);
        if(byUserName.isEmpty()){
        return null;
        } else if (byUserName.get().getUserName().equals(username)) {
            return new User(byUserName.get().getUserName(),byUserName.get().getPassword(),new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("User is not exist");
        }
    }
}
