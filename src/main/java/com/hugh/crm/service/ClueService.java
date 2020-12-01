package com.hugh.crm.service;

import com.hugh.crm.pojo.Clue;
import com.hugh.crm.pojo.Tran;


import java.util.List;

public interface ClueService {
    Boolean createClue(Clue clue);

    Clue getDetail(String id);

    List<Clue> getAllClue();

    Integer saveClueAsTran(Tran tran);

    Integer saveContactsActivity(String uuid, String contactId, String activityId);
}
