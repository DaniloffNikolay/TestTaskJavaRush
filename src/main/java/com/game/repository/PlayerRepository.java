package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "player_repository")
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
