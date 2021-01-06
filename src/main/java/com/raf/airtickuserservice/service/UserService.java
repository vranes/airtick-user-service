package com.raf.airtickuserservice.service;

import com.raf.airtickuserservice.domain.CreditCard;
import com.raf.airtickuserservice.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);

    DiscountDto findDiscount(Long id);

    UserDto findById(Long id);

    Long findIdByEmail(String email);

    List<CreditCardDto> findCardsById(Long id);

    UserDto add(UserCreateDto userCreateDto);

    UserDto addCardToUser (Long id, CreditCardCreateDto creditCardCreateDto);

    UserDto mailVerification(Long id);

    UserDto update(Long id, UserUpdateDto userUpdateDto);

    UserDto updateMiles(Long id, Integer miles);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
}
