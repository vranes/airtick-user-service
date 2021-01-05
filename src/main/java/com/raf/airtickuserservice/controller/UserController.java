package com.raf.airtickuserservice.controller;

import com.raf.airtickuserservice.dto.*;
import com.raf.airtickuserservice.email.EmailService;
import com.raf.airtickuserservice.security.CheckSecurity;
import com.raf.airtickuserservice.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Formatter;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }
    @ApiOperation(value = "Get all users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/find-id")
    public ResponseEntity<Long> findIdByEmail(@RequestBody @Valid String email) {
        return new ResponseEntity<>(userService.findIdByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/{id}/discount")
    public ResponseEntity<DiscountDto> getDiscount(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findDiscount(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Register user")
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.add(userCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Verify mail")
    @GetMapping("/{id}/mail-verification")
    public ResponseEntity<UserDto> mailVerification(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.mailVerification(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        System.out.println("im in login start");
        System.out.println(tokenRequestDto);

        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Update profile")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateProfile(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.update(id, userUpdateDto));
    }

    @ApiOperation(value = "Update miles")
    @PutMapping("/{id}/miles")
    public ResponseEntity<UserDto> updateMiles(@PathVariable("id") Long id, @RequestBody @Valid Integer miles) {
        return ResponseEntity.ok(userService.updateMiles(id, miles));
    }

    @ApiOperation(value = "Add a credit card")
    @PutMapping("/{id}/new-card")
    public ResponseEntity<UserDto> addCard(@PathVariable("id") Long id, @RequestBody @Valid CreditCardCreateDto creditCardCreateDto) {
        return ResponseEntity.ok(userService.addCardToUser(id, creditCardCreateDto));
    }

}
