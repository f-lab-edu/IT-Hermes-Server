package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.UserService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/user")
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

    @RequestMapping(value = "/duplicate-nickname", method = RequestMethod.POST)
    public ResponseEntity<UserDuplicateNicknameResponseDto> checkDuplicateNickname(@Valid @RequestBody UserDuplicateNicknameRequestDto userDuplicateNicknameRequestDto) {
        UserDuplicateNicknameResponseDto userDuplicateNicknameResponseDto = userService.checkDuplicateNickname(userDuplicateNicknameRequestDto);
        return ResponseEntity.ok(userDuplicateNicknameResponseDto);
    }

    @RequestMapping(value = "/duplicate-id", method = RequestMethod.POST)
    public ResponseEntity<UserDuplicateIdResponseDto> checkDuplicateNickname(@Valid @RequestBody UserDuplicateIdRequestDto userDuplicateIdRequestDto) {
        UserDuplicateIdResponseDto userDuplicateIdResponseDto = userService.checkDuplicateId(userDuplicateIdRequestDto);
        return ResponseEntity.ok(userDuplicateIdResponseDto);
    }

    @RequestMapping(value = "/nickname", method = RequestMethod.PUT)
    public ResponseEntity<UserUpdateNicknameResponseDto> checkDuplicateNickname(@Valid @RequestBody UserUpdateNicknameRequestDto userUpdateNicknameRequestDto) {
        UserUpdateNicknameResponseDto userUpdateNicknameResponseDto = userService.updateNickname(userUpdateNicknameRequestDto);
        return ResponseEntity.ok(userUpdateNicknameResponseDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> deleteUser(@Valid @RequestBody UserDeleteUserRequestDto userDeleteUserRequestDto) {
        CommonResponseDto commonResponseDto = userService.deleteUser(userDeleteUserRequestDto);
        return ResponseEntity.ok(commonResponseDto);
    }

    @RequestMapping(value = "/my-page", method = RequestMethod.POST)
    public ResponseEntity<UserFindMyDataResponseDto> findMyData(@Valid @RequestBody UserFindMyDataRequestDto userFindMyDataRequestDto) {
        UserFindMyDataResponseDto userFindMyDataResponseDto = userService.findMyData(userFindMyDataRequestDto);
        return ResponseEntity.ok(userFindMyDataResponseDto);
    }

}
