package com.examly.springapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;

    @NotBlank(message = "Player name is required")
    @Size(min = 2, max = 50, message = "Player name must be between 2 and 50 characters")
    private String playerName;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 40, message = "Age must be at most 40")
    private Integer age;

    @NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 30, message = "Country must be between 2 and 30 characters")
    private String country;

    public Player() {}

    public Player(Integer playerId, String playerName, Integer age, String role, String country) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.age = age;
        this.role = role;
        this.country = country;
    }

    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    // Validation helper (for manual role enforcement)
    public static boolean isValidRole(String role) {
        return role != null && (
            role.equals("Batsman") ||
            role.equals("Bowler") ||
            role.equals("All-rounder") ||
            role.equals("Wicketkeeper")
        );
    }
}