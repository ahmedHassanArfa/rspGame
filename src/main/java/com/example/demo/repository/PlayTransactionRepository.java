package com.example.demo.repository;

import com.example.demo.entities.PlayTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface PlayTransactionRepository extends CrudRepository<PlayTransaction, Long> {

    @Query("select pt.winningStatus, count(pt.winningStatus) from PlayTransaction pt where pt.game.id= :gameId group by pt.winningStatus")
    Map<String,Long> getFinalResultByGameId(Long gameId);

}
