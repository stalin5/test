package com.examly.springapp.repository;

import com.examly.springapp.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Page<Player> findByRole(String role, Pageable pageable);
    Page<Player> findByPlayerNameContainingIgnoreCase(String playerName, Pageable pageable);
    Page<Player> findByRoleAndPlayerNameContainingIgnoreCase(String role, String playerName, Pageable pageable);
}