package com.hermes.ithermes.infrastructure;

import com.hermes.ithermes.domain.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private final EntityManager em;

    @Autowired
    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public String save(User user) {
        user.initDefaultValue();
        em.persist(user);
        return user.getLoginId();
    }

    public List<User> findByUserId(String loginId) {
        return em.createQuery("select u from User u where u.loginId = :loginId", User.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }

    public List<User> findByUserNickname(String nickname) {
        return em.createQuery("select u from User u where u.nickname = :nickname", User.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<User> findUserIdAndPassword(String loginId, String password) {
        return em.createQuery("select u from User u where u.loginId = :loginId and u.password= :password", User.class)
                .setParameter("loginId", loginId)
                .setParameter("password", password)
                .getResultList();
    }
}
