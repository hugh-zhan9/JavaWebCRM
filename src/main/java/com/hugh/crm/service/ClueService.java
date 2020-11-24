package com.hugh.crm.service;

import com.hugh.crm.pojo.Clue;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClueService {
    Boolean createClue(Clue clue);

    Clue getDetail(String id);

    List<Clue> getAllClue();
}
