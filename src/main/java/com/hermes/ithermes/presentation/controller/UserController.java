package com.hermes.ithermes.presentation.controller;

import com.hermes.ithermes.application.UserService;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.user.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<UserCreateUserRequestDto> joinUser(@Valid @RequestBody UserCreateUserRequestDto userCreateUserRequestDto) {
        userService.joinUser(userCreateUserRequestDto);
        URI uri = URI.create("/api/user/join/");

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

    @RequestMapping(value = "/user-list", method = RequestMethod.GET)
    public ResponseEntity<List<UserFindUserListResponseDto>> findUserList() {
        List<UserFindUserListResponseDto> userList = userService.findUserList();
        return ResponseEntity.ok(userList);
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

    @RequestMapping(value = "/refresh-token", method = RequestMethod.GET)
    public ResponseEntity<UserCheckRefreshTokenResponseDto> checkRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("HERMES-REFRESH-TOKEN");
        UserCheckRefreshTokenResponseDto userCheckRefreshTokenResponseDto = userService.checkRefreshToken(refreshToken);
        return ResponseEntity.ok(userCheckRefreshTokenResponseDto);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<CommonResponseDto> logoutUser(Authentication authentication) {
        String loginId = authentication.getName();
        CommonResponseDto commonResponseDto = userService.userLogout(loginId);
        return ResponseEntity.ok(commonResponseDto);
    }
}
