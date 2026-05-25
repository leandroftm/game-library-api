package com.leandroftm.game_library_api.domain.entity;

import com.leandroftm.game_library_api.domain.enums.GameStatus;
import com.leandroftm.game_library_api.exception.domain.user_game.GameStatusAlreadyDroppedException;
import com.leandroftm.game_library_api.exception.domain.user_game.GameStatusAlreadyPlayingException;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "user_game")
public class UserGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    private Long igdbId;
    @Column(nullable = false)
    private String gameName;
    @Column(nullable = false)
    private boolean favorite;
    @Column(nullable = false)
    private String platformName;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime completedDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public UserGame(Long igdbId, String gameName, boolean favorite, String platformName) {
        this.igdbId = igdbId;
        this.gameName = gameName;
        this.favorite = favorite;
        this.platformName = platformName;
        this.status = GameStatus.TO_PLAY;
    }

    public void associateUser(User user) {
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public void startPlaying() {
        if (this.status == GameStatus.PLAYING)
            throw new GameStatusAlreadyPlayingException();
        if (this.startDate == null)
            this.startDate = LocalDateTime.now();
        this.status = GameStatus.PLAYING;
        this.completedDate = null;
    }

    public void dropGame() {
        if (this.status == GameStatus.DROPPED)
            throw new GameStatusAlreadyDroppedException();
        this.status = GameStatus.DROPPED;
        this.startDate = null;
        this.completedDate = null;
    }

    public void completeGame() {
        if (this.status != GameStatus.PLAYING)
            throw new IllegalStateException("Only playing games can be completed");
        this.status = GameStatus.COMPLETED;
        this.completedDate = LocalDateTime.now();
    }
}
