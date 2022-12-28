package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.UserService;
import com.hermes.ithermes.presentation.dto.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<UserCreateUserResponseDto> joinUser(@Valid @RequestBody UserCreateUserRequestDto userCreateUserRequestDto
            , BindingResult bindingResult) {

        UserCreateUserResponseDto userCreateUserResponseDto = userService.joinUser(userCreateUserRequestDto);
        return ResponseEntity.ok(userCreateUserResponseDto);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponseDto> loginUser(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        UserLoginResponseDto userLoginResponseDto = userService.loginUser(userLoginRequestDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }

    @RequestMapping(value = "/duplicate/nickname", method = RequestMethod.POST)
    public ResponseEntity<UserDuplicateNicknameResponseDto> isCheckDuplicateNickname(@Valid @RequestBody UserDuplicateNicknameRequestDto userDuplicateNicknameRequestDto) {
        UserDuplicateNicknameResponseDto userDuplicateNicknameResponseDto = userService.isCheckDuplicateNickname(userDuplicateNicknameRequestDto);
        return ResponseEntity.ok(userDuplicateNicknameResponseDto);
    }

    @RequestMapping(value = "/duplicate/id", method = RequestMethod.POST)
    public ResponseEntity<UserDuplicateIdResponseDto> isCheckDuplicateNickname(@Valid @RequestBody UserDuplicateIdRequestDto userDuplicateIdRequestDto) {
        UserDuplicateIdResponseDto userDuplicateIdResponseDto = userService.isCheckDuplicateId(userDuplicateIdRequestDto);
        return ResponseEntity.ok(userDuplicateIdResponseDto);
    }

    @RequestMapping(value = "/nickname", method = RequestMethod.PUT)
    public ResponseEntity<UserChangeNicknameResponseDto> changeNickname(@Valid @RequestBody UserChangeNicknameRequestDto userChangeNicknameRequestDto) {
        UserChangeNicknameResponseDto userLoginResponseDto = userService.changeNickname(userChangeNicknameRequestDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }
}
