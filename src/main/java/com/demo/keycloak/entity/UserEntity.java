package com.demo.keycloak.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
@Entity
@NamedQueries({
        @NamedQuery(name = "findAll", query = "FROM UserEntity u"),
        @NamedQuery(name = "findByUserIdLike", query = "FROM UserEntity u WHERE u.id LIKE CONCAT('%', :id, '%')"),
        @NamedQuery(name = "findByUserId", query = "FROM UserEntity u WHERE u.id = :id"),
        //@NamedQuery(name = "findByUserEmail", query = "FROM UserEntity u WHERE u.email = :email")
})
public class UserEntity {

    @Id
    private String id;
    @Column(name = "username")
    private String name;
    @Column(name = "login_id")
    private String loginId;
    private String password;
    private String status;
    @Column(name="created_at")
    private LocalDateTime createdAt;
}
