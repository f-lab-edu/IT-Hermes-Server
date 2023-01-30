package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.UserService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Value("${telegram-key}")
    private String telegramKey;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<UserCreateUserRequestDto> joinUser(@Valid @RequestBody UserCreateUserRequestDto userCreateUserRequestDto) {
        userService.joinUser(userCreateUserRequestDto);
        URI uri = URI.create("user/join/");

        return ResponseEntity.created(uri).body(userCreateUserRequestDto);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponseDto> loginUser(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        UserLoginResponseDto userLoginResponseDto = userService.loginUser(userLoginRequestDto);
        return ResponseEntity.ok().body(userLoginResponseDto);
    }

    @RequestMapping(value = "/duplicate-nickname", method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> checkDuplicateNickname(@Valid @RequestBody UserDuplicateNicknameRequestDto userDuplicateNicknameRequestDto) {
        CommonResponseDto userDuplicateNicknameResponseDto = userService.checkDuplicateNickname(userDuplicateNicknameRequestDto);
        return ResponseEntity.ok(userDuplicateNicknameResponseDto);
    }

    @RequestMapping(value = "/duplicate-id", method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> checkDuplicateNickname(@Valid @RequestBody UserDuplicateIdRequestDto userDuplicateIdRequestDto) {
        CommonResponseDto userDuplicateIdResponseDto = userService.checkDuplicateId(userDuplicateIdRequestDto);
        return ResponseEntity.ok(userDuplicateIdResponseDto);
    }

    @RequestMapping(value = "/nickname", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> checkDuplicateNickname(@Valid @RequestBody UserUpdateNicknameRequestDto userUpdateNicknameRequestDto) {
        CommonResponseDto userUpdateNicknameResponseDto = userService.updateNickname(userUpdateNicknameRequestDto);
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

    @RequestMapping(value = "/telegramId", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> updateTelegramId() {
        return ResponseEntity.ok(userService.updateTelegramId(telegramKey));
    }
}
