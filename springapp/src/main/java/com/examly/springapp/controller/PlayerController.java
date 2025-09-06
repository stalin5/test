package com.examly.springapp.controller;

import com.examly.springapp.model.Player;
import com.examly.springapp.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;
    @Autowired
    public PlayerController(PlayerService playerService) { this.playerService = playerService; }

    @PostMapping
    public ResponseEntity<?> createPlayer(@Valid @RequestBody Player player) {
        try {
            Player created = playerService.createPlayer(player);
            return ResponseEntity.status(201).body(created);
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPlayers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String sortBy,
        @RequestParam(required = false) String role,
        @RequestParam(required = false) String search
    ) {
        Page<Player> result = playerService.getPlayers(page, size, sortBy, role, search);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<?> getPlayerById(@PathVariable Integer playerId) {
        Player maybePlayer = playerService.getPlayerById(playerId).orElse(null);
        if (maybePlayer != null) {
            return ResponseEntity.ok(maybePlayer);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Player not found");
            return ResponseEntity.status(404).body(error);
        }
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<?> updatePlayer(
        @PathVariable Integer playerId,
        @Valid @RequestBody Player player
    ) {
        try {
            Player updated = playerService.updatePlayer(playerId, player);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (RuntimeException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());
            return ResponseEntity.status(404).body(error);
        }
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<?> deletePlayer(@PathVariable Integer playerId) {
        try {
            playerService.deletePlayer(playerId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());
            return ResponseEntity.status(404).body(error);
        }
    }
}
