package com.raf.airtickuserservice.controller;

import com.raf.airtickuserservice.dto.*;
import com.raf.airtickuserservice.email.EmailService;
import com.raf.airtickuserservice.security.CheckSecurity;
import com.raf.airtickuserservice.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/{id}/discount")
    public ResponseEntity<DiscountDto> getDiscount(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findDiscount(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/mail-verification")                                              // TODO
    public ResponseEntity<UserDto> mailVerification(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Register user")
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.add(userCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Update profile")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        UserDto userDto = userService.findById(id);
        if (! userDto.getEmail().equals(userUpdateDto.getEmail()))
            emailService.sendSimpleMessage(userUpdateDto.getEmail(), "Confirmation Mail", "To confirm mail change, click on the link: http://localhost:8082/api/" + id + "/mail-verification");
        return ResponseEntity.ok(userService.update(id, userUpdateDto));
    }

    @ApiOperation(value = "Update miles")
    @PutMapping("/{id}/miles")
    public ResponseEntity<UserDto> update(@PathVariable("id") Long id, @RequestBody @Valid Integer miles) {
        return ResponseEntity.ok(userService.updateMiles(id, miles));
    }
}
