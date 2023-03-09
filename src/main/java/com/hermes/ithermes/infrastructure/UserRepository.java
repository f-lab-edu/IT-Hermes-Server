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

    boolean existsByNickname(@Param("nickname") String nickname);

    boolean existsByLoginId(@Param("loginId") String loginId);

    Boolean existsUserByNicknameAndTelegramId(String nickname,String telegramId);

    Boolean existsUserByNickname(String nickname);

    @Query("SELECT u.id FROM User u where u.telegramId is not null and u.isDelete=false")
    List<Long> findUserId();

    @Query("SELECT u.telegramId FROM User u where u.id=:id")
    String findTelegramIdByUserId(Long id);

    User findByNickname(String nickname);

    User findUsersById(Long id);

}
