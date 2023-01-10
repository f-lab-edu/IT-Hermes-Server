package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(@Param("loginId") String loginId);

    Optional<User> findByLoginIdAndPasswordAndIsDelete(@Param("loginId") String loginId, @Param("password") String password, @Param("isDelete") boolean isDelete);

    Optional<User> findByNickname(@Param("nickname") String nickname);
}
