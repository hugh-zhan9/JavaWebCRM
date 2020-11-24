package com.hugh.crm.service;

import com.hugh.crm.pojo.Activity;
import com.hugh.crm.pojo.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {
    List<Activity> getActivityByClueId(String clueId);

    Boolean deleteClueActivityById(String id);

    Boolean saveRelation(ClueActivityRelation clueActivityRelation);
}
