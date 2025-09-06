package com.examly.springapp.service;

import com.examly.springapp.model.Player;
import com.examly.springapp.repository.PlayerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(@Valid Player player) {
        if (!Player.isValidRole(player.getRole())) {
            throw new IllegalArgumentException("Role must be one of: Batsman, Bowler, All-rounder, Wicketkeeper");
        }
        return playerRepository.save(player);
    }

    public Page<Player> getPlayers(int page, int size, String sortBy, String role, String search) {
        Pageable pageable = (sortBy == null || sortBy.isBlank())
            ? PageRequest.of(page, size)
            : PageRequest.of(page, size, Sort.by(sortBy).ascending());
        if (role != null && !role.isBlank() && search != null && !search.isBlank()) {
            return playerRepository.findByRoleAndPlayerNameContainingIgnoreCase(role, search, pageable);
        } else if (role != null && !role.isBlank()) {
            return playerRepository.findByRole(role, pageable);
        } else if (search != null && !search.isBlank()) {
            return playerRepository.findByPlayerNameContainingIgnoreCase(search, pageable);
        } else {
            return playerRepository.findAll(pageable);
        }
    }

    public Optional<Player> getPlayerById(Integer playerId) {
        return playerRepository.findById(playerId);
    }

    @Transactional
    public Player updatePlayer(Integer playerId, @Valid Player playerDetails) {
        Player player = playerRepository.findById(playerId)
            .orElseThrow(() -> new RuntimeException("Player not found"));
        if (!Player.isValidRole(playerDetails.getRole())) {
            throw new IllegalArgumentException("Role must be one of: Batsman, Bowler, All-rounder, Wicketkeeper");
        }
        player.setPlayerName(playerDetails.getPlayerName());
        player.setAge(playerDetails.getAge());
        player.setRole(playerDetails.getRole());
        player.setCountry(playerDetails.getCountry());
        return playerRepository.save(player);
    }

    public void deletePlayer(Integer playerId) {
        Player player = playerRepository.findById(playerId)
            .orElseThrow(() -> new RuntimeException("Player not found"));
        playerRepository.delete(player);
    }
}
