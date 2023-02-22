
package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.Keyword;
import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.entity.UserKeywordRegistry;
import com.hermes.ithermes.domain.exception.*;
import com.hermes.ithermes.domain.factory.KeywordFactory;
import com.hermes.ithermes.domain.factory.UserFactory;
import com.hermes.ithermes.domain.factory.UserKeywordRegistryFactory;
import com.hermes.ithermes.infrastructure.UserKeywordRegistryRepository;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.presentation.dto.CommonResponseDto;
import com.hermes.ithermes.presentation.dto.user.*;
import com.hermes.ithermes.presentation.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey";

    private final UserKeywordRegistryRepository userKeywordRegistryRepository;
    private final UserFactory userFactory;
    private final KeywordFactory keywordFactory;
    private final UserRepository userRepository;
    private final UserKeywordRegistryFactory userKeywordRegistryFactory;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public CommonResponseDto joinUser(UserCreateUserRequestDto userLoginRequestDto) {

        Optional.ofNullable(userLoginRequestDto.getPassword().equals(userLoginRequestDto.getPasswordConfirm()))
                .filter(v -> v)
                .orElseThrow(() -> new UnMatchedPasswordException());

        String loginId = userLoginRequestDto.getId();
        String password = encoder.encode(userLoginRequestDto.getPassword());
        userLoginRequestDto.encodingPassword(password);

        if (userFactory.existsByLoginId(loginId)) throw new SameIdException();
        User user = userFactory.parseLoginRequestDtoToUser(userLoginRequestDto);

        Arrays.stream(userLoginRequestDto.getKeywordList())
                .filter(v -> Objects.nonNull(v))
                .forEach(v -> {
                    Keyword keyword = keywordFactory.parseKeywordNameToKeyword(v);
                    UserKeywordRegistry userKeywordRegistry = userKeywordRegistryFactory.parseUserAndKeyword(user, keyword);
                    userKeywordRegistryRepository.save(userKeywordRegistry);
                });

        return new CommonResponseDto();
    }

    @Transactional
    public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
        String loginId = userLoginRequestDto.getId();
        String password = userLoginRequestDto.getPassword();

        User user = userFactory.findLoginId(loginId).orElseThrow(() -> new WrongIdOrPasswordException());

        if (!encoder.matches(password, user.getPassword())) throw new WrongIdOrPasswordException();

        String refreshToken = jwtUtil.createRefreshToken(loginId);
        user.updateRefreshToken(refreshToken);

        UserLoginResponseDto userLoginResponseDto = UserLoginResponseDto.builder()
                .message("success")
                .accessToken(jwtUtil.createAccessToken(loginId))
                .refreshToken(refreshToken)
                .build();
        return userLoginResponseDto;
    }

    public CommonResponseDto checkDuplicateNickname(UserDuplicateNicknameRequestDto userDuplicateNicknameRequestDto) {
        String nickname = userDuplicateNicknameRequestDto.getNickname();
        if (userRepository.existsByNickname(nickname)) throw new SameNicknameException();
        return new CommonResponseDto();
    }

    public CommonResponseDto checkDuplicateId(UserDuplicateIdRequestDto userDuplicateIdRequestDto) {
        String loginId = userDuplicateIdRequestDto.getId();
        if (userFactory.existsByLoginId(loginId)) throw new SameIdException();
        return new CommonResponseDto();
    }

    @Transactional
    public CommonResponseDto updateNickname(UserUpdateNicknameRequestDto userUpdateNicknameRequestDto) {
        String newNickname = userUpdateNicknameRequestDto.getNickname();
        if (userFactory.existsByNickname(newNickname)) throw new SameNicknameException();

        User user = userFactory.findLoginId(userUpdateNicknameRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());
        user.updateNickname(newNickname);
        return new CommonResponseDto();
    }

    @Transactional
    public CommonResponseDto deleteUser(UserDeleteUserRequestDto userDeleteUserRequestDto) {
        User user = userFactory.findLoginId(userDeleteUserRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());

        user.isDelete();
        return new CommonResponseDto();
    }

    public UserFindMyDataResponseDto findMyData(UserFindMyDataRequestDto userFindMyDataRequestDto) {
        User user = userFactory.findLoginId(userFindMyDataRequestDto.getId()).orElseThrow(() -> new WrongIdOrPasswordException());
        return new UserFindMyDataResponseDto(user.getLoginId(), user.getNickname());
    }

    public UserCheckRefreshTokenResponseDto checkRefreshToken(String refreshToken) {
        String token = refreshToken.split(" ")[1];
        boolean validateToken = jwtUtil.validateToken(token, secretKey);
        if (!validateToken) {
            throw new ExpireTokenException();
        }
        String loginId = JwtUtil.getUsername(token, secretKey);
        User user = userFactory.findLoginId(loginId).orElseThrow(() -> new ExpireTokenException());
        String storedRefreshToken = user.getRefreshToken();
        if (!token.equals(storedRefreshToken)) throw new ExpireTokenException();
        String accessToken = jwtUtil.createAccessToken(loginId);
        return UserCheckRefreshTokenResponseDto
                .builder()
                .accessToken(accessToken)
                .message("success")
                .build();
    }

    @Transactional
    public CommonResponseDto userLogout(String loginId) {
        User user = userFactory.findLoginId(loginId).orElseThrow(()->new WrongIdOrPasswordException());
        user.updateRefreshToken(null);
        return new CommonResponseDto();
    }

}