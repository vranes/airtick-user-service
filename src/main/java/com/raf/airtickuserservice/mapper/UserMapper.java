package com.raf.airtickuserservice.mapper;

import com.raf.airtickuserservice.domain.User;
import com.raf.airtickuserservice.dto.UserCreateDto;
import com.raf.airtickuserservice.dto.UserDto;
import com.raf.airtickuserservice.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPassport(user.getPassport());
        userDto.setMiles(user.getMiles());
        return userDto;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setPassport(userCreateDto.getPassport());
        user.setPassword(userCreateDto.getPassword());
       // user.setRole(roleRepository.findRoleByName("ROLE_USER").get()); // TODO
        user.setMiles(0);
        // TODO Set cards ?

        return user;
    }
}
