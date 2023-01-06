package com.demo.keycloak.repository;

import com.demo.keycloak.entity.UserEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 외부 DB 리포지토리 (Hibernate 사용)
 *
 */
@RequiredArgsConstructor
public class CustomUserStorageRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public List<UserEntity> findByUserIdLike(String userId) {
        List<UserEntity> result = entityManager.createNamedQuery("SELECT u.* FROM UserEntity u WHERE u.userId like :userId",UserEntity.class).setParameter("userId", userId).getResultList();
        System.out.println("\n\n");
        System.out.println(result);
        System.out.println(result.size());
        System.out.println("\n\n");
        return result;
    }
}
