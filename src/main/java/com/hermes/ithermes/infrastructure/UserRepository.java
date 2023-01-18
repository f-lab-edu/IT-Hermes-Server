package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(@Param("loginId") String loginId);

    Optional<User> findByLoginIdAndPasswordAndIsDelete(@Param("loginId") String loginId, @Param("password") String password, @Param("isDelete") boolean isDelete);

    Optional<User> findByNickname(@Param("nickname") String nickname);

    Boolean existsUserByNicknameAndTelegramId(String nickname,String telegramId);

    Boolean existsUserByNickname(String nickname);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.telegramId=:telegramId WHERE u.nickname=:nickname")
    int updateTelegramIdByNickname(String telegramId,String nickname);

    List<User> findByTelegramIdIsNotNull();

    @Query("SELECT u.telegramId FROM User u where u.id=:id")
    String findTelegramIdByUserId(Long id);

}
