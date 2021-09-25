package com.example.demo.repository;

import com.example.demo.entities.Game;
import com.example.demo.models.GameStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    @Modifying
    @Query("update Game g set g.status= :status where g.id= :id")
    void updateStatusById(GameStatus status, Long id);

}
