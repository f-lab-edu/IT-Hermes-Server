package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(@Param("loginId") String loginId);

    boolean existsByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);

    boolean existsByNickname(@Param("nickname") String nickname);

    boolean existsByLoginId(@Param("userId") String userId);

    Boolean existsUserByNicknameAndTelegramId(String nickname,String telegramId);

    Boolean existsUserByNickname(String nickname);

    List<User> findByTelegramIdIsNotNull();

    @Query("SELECT u.telegramId FROM User u where u.id=:id")
    String findTelegramIdByUserId(Long id);

    User findUsersByNickname(String nickname);

}
