package com.raf.airtickuserservice.service;

import com.raf.airtickuserservice.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);

    DiscountDto findDiscount(Long id);

    UserDto add(UserCreateDto userCreateDto);

    UserDto update(UserUpdateDto userUpdateDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
}
