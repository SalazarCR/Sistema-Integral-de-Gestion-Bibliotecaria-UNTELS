package pe.edu.untels.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad para rastrear tokens/sesiones invalidadas (logout)
 */
@Entity
@Table(name = "TokenBlacklist")
public class TokenBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTokenBlacklist;

    @Column(name = "idUser", nullable = false)
    private int idUser;

    @Column(name = "sessionToken", length = 500, nullable = true)
    private String sessionToken;

    @Column(name = "logout_timestamp", nullable = false)
    private LocalDateTime logoutTimestamp;

    @Column(name = "reason", length = 200)
    private String reason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public TokenBlacklist() {
        this.createdAt = LocalDateTime.now();
    }

    public TokenBlacklist(int idUser, LocalDateTime logoutTimestamp, String reason) {
        this.idUser = idUser;
        this.logoutTimestamp = logoutTimestamp;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
    }

    public TokenBlacklist(int idUser, String sessionToken, LocalDateTime logoutTimestamp, String reason) {
        this.idUser = idUser;
        this.sessionToken = sessionToken;
        this.logoutTimestamp = logoutTimestamp;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
    }

    // Getters y Setters
    public int getIdTokenBlacklist() {
        return idTokenBlacklist;
    }

    public void setIdTokenBlacklist(int idTokenBlacklist) {
        this.idTokenBlacklist = idTokenBlacklist;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public LocalDateTime getLogoutTimestamp() {
        return logoutTimestamp;
    }

    public void setLogoutTimestamp(LocalDateTime logoutTimestamp) {
        this.logoutTimestamp = logoutTimestamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

