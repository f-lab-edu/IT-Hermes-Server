package com.hermes.ithermes.application;

import com.hermes.ithermes.domain.entity.User;
import com.hermes.ithermes.domain.exception.*;
import com.hermes.ithermes.infrastructure.UserRepository;
import com.hermes.ithermes.presentation.dto.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserCreateUserResponseDto joinUser(UserCreateUserRequestDto userLoginRequestDto) {
        if (!isCheckPassword(userLoginRequestDto.getPassword(), userLoginRequestDto.getPasswordConfirm())) {
            throw new UnMatchedPasswordException();
        }
        if (findUserId(userLoginRequestDto.getId()).isEmpty()) {
            userRepository.save(userLoginRequestDto.parseUserToLoginRequest(userLoginRequestDto));
            return new UserCreateUserResponseDto("success");
        } else {
            throw new SameUserException();
        }
    }

    public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
        if(findUserIdAndPassword(userLoginRequestDto.getId(),userLoginRequestDto.getPassword()).isEmpty()) {
            throw new WrongIdOrPasswordException();
        } else {
            return new UserLoginResponseDto("success");
        }
    }

    public UserDuplicateNicknameResponseDto isCheckDuplicateNickname(UserDuplicateNicknameRequestDto userDuplicateNicknameRequestDto) {
        if (findUserNickname(userDuplicateNicknameRequestDto.getNickname()).isEmpty()) {
            return new UserDuplicateNicknameResponseDto("success");
        } else {
            throw new SameNicknameException();
        }
    }

    public UserDuplicateIdResponseDto isCheckDuplicateId(UserDuplicateIdRequestDto userDuplicateIdRequestDto) {
        if (findUserNickname(userDuplicateIdRequestDto.getId()).isEmpty()) {
            return new UserDuplicateIdResponseDto("success");
        } else {
            throw new SameIdException();
        }
    }

    @Transactional
    public List<User> findUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public List<User> findUserNickname(String nickname) {
        return userRepository.findByUserNickname(nickname);
    }

    @Transactional
    public List<User> findUserIdAndPassword(String id, String password) {
        return userRepository.findUserIdAndPassword(id, password);
    }

    public UserChangeNicknameResponseDto changeNickname(UserChangeNicknameRequestDto userChangeNicknameRequestDto) {
        List<User> userList = findUserNickname(userChangeNicknameRequestDto.getNickname());
        if (userList.isEmpty()) {
        } else {
        }
        return new UserChangeNicknameResponseDto("success");
    }

    public boolean isCheckPassword(String password, String passwordConfirm) {
        if (password.equals(passwordConfirm)) {
            return true;
        } else {
            return false;
        }
    }
}
