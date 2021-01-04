package com.raf.airtickuserservice.service.impl;

import com.raf.airtickuserservice.domain.User;
import com.raf.airtickuserservice.domain.UserRank;
import com.raf.airtickuserservice.dto.*;
import com.raf.airtickuserservice.email.EmailService;
import com.raf.airtickuserservice.exception.CustomException;
import com.raf.airtickuserservice.exception.ErrorCode;
import com.raf.airtickuserservice.exception.NotFoundException;
import com.raf.airtickuserservice.mapper.CardMapper;
import com.raf.airtickuserservice.mapper.UserMapper;
import com.raf.airtickuserservice.repository.UserRankRepository;
import com.raf.airtickuserservice.repository.UserRepository;
import com.raf.airtickuserservice.security.service.TokenService;
import com.raf.airtickuserservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private TokenService tokenService;
    private UserRepository userRepository;
    private UserRankRepository userRankRepository;
    private UserMapper userMapper;
    private CardMapper cardMapper;
    private EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, TokenService tokenService, UserRankRepository userRankRepository, UserMapper userMapper, CardMapper cardMapper, EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.userRankRepository = userRankRepository;
        this.cardMapper = cardMapper;
        this.emailService = emailService;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public DiscountDto findDiscount(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with id: %d not found.", id)));
        List<UserRank> userRankList = userRankRepository.findAll();
        //get discount
        Integer discount = userRankList.stream()
                .filter(userRank -> userRank.getMaxMiles() >= user.getMiles()
                        && userRank.getMinMiles() <= user.getMiles())
                .findAny()
                .get()
                .getDiscount();
        return new DiscountDto(discount);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with requested id not found"));
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto add(UserCreateDto userCreateDto) {
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        user.setVerified(false);
        userRepository.save(user);
        emailService.sendSimpleMessage(userCreateDto.getEmail(), "Confirmation Mail", "To continue registration, click on the link: http://localhost:8082/api/" + user.getId() + "/mail-verification");
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto mailVerification(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with requested id not found"));
        user.setVerified(true);
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto addCardToUser(Long id, CreditCardCreateDto creditCardCreateDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        user.getCards().add(cardMapper.CreditCardCreateDtoToCreditCard(creditCardCreateDto));
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto update(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        if (! user.getEmail().equals(userUpdateDto.getEmail())){
            user.setVerified(false);
            emailService.sendSimpleMessage(userUpdateDto.getEmail(), "Confirmation Mail", "To confirm mail change, click on the link: http://localhost:8082/api/" + id + "/mail-verification");
        }
        //Set values
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());
        user.setPassport(userUpdateDto.getPassport());
        user.setPassword(userUpdateDto.getPassword());
        user.setEmail(userUpdateDto.getEmail());
        user.setMiles(userUpdateDto.getMiles());
        //Map to DTO and return it
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateMiles(Long id, Integer miles) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        //Set miles
        user.setMiles(miles);
        //Map to DTO and return it
        return userMapper.userToUserDto(userRepository.save(user));

    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials

        User user = userRepository
                .findUserByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with email: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));
        if (!user.getVerified())
            throw new CustomException("Please verify your e-mail address before logging in", ErrorCode.EMAIL_NOT_VERIFIED, HttpStatus.PRECONDITION_FAILED);
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }
}
