package com.hugh.crm.dao;

import com.hugh.crm.pojo.Clue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClueDao {
    Integer insertClue(Clue clue);

    Clue getClueById(String id);

    List<Clue> getAllClue();
}
