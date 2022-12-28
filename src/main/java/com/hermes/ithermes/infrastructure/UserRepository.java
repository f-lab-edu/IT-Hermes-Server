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
        return user.getUserId();
    }

    public List<User> findByUserId(String userId) {
        return em.createQuery("select u from User u where u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<User> findByUserNickname(String nickname) {
        return em.createQuery("select u from User u where u.nickname = :nickname", User.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<User> findUserIdAndPassword(String id, String password) {
        return em.createQuery("select u from User u where u.userId = :userId and u.password= :password", User.class)
                .setParameter("userId", id)
                .setParameter("password", password)
                .getResultList();
    }
}
