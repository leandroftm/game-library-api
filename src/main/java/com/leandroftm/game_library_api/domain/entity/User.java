package com.leandroftm.game_library_api.domain.entity;

import com.leandroftm.game_library_api.exception.domain.user.UserAlreadyActiveException;
import com.leandroftm.game_library_api.exception.domain.user.UserAlreadyInactiveException;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 25)
    private String userName;
    @Column(nullable = false, length = 25)
    private String password;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    private LocalDateTime disabledAt;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserGame> games = new ArrayList<>() ;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
        this.active = true;
    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public void addGame(UserGame game) {
        game.associateUser(this);
        this.games.add(game);
    }

    public void updateDetails(String userName, String password, String email) {
        if (userName != null)
            this.userName = userName;
        if (password != null)
            this.password = password;
        if (email != null)
            this.email = email;
    }

    public void enable() {
        if (this.active)
            throw new UserAlreadyActiveException();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
    }

    public void disable() {
        if (!this.active)
            throw new UserAlreadyInactiveException();
        this.active = false;
        this.updatedAt = LocalDateTime.now();
        this.disabledAt = updatedAt;
    }
}
