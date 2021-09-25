package com.example.demo.repository;

import com.example.demo.entities.Game;
import com.example.demo.models.GameStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    Game updateStatusById(GameStatus status, Long id);

}
