package com.hugh.crm.service.impl;

import com.hugh.crm.dao.*;
import com.hugh.crm.pojo.Clue;
import com.hugh.crm.pojo.Tran;
import com.hugh.crm.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    ClueDao clueDao;
    @Autowired
    ClueRemarkDao clueRemarkDao;
    @Autowired
    ClueActivityRelationDao clueActivityRelationDao;
    @Autowired
    TranDao tranDao;
    @Autowired
    ContactsDao contactsDao;

    @Override
    public Boolean createClue(Clue clue) {
        Boolean flag = true;
        Integer count =  clueDao.insertClue(clue);

        if (count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Clue getDetail(String id) {
        Clue clue = clueDao.getClueById(id);
        return clue;
    }

    @Override
    public List<Clue> getAllClue() {
        List<Clue> clues = clueDao.getAllClue();
        return clues;
    }

    @Override
    public Integer saveClueAsTran(Tran tran) {
        Integer count = tranDao.saveTran(tran);
        return count;
    }

    @Override
    public Integer saveContactsActivity(String id, String contactId, String activityId) {
        contactsDao.saveContactsActivity(id,contactId,activityId);
        return null;
    }
}
