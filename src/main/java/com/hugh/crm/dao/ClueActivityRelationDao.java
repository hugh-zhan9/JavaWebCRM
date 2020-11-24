package com.hugh.crm.dao;

import com.hugh.crm.pojo.Activity;
import com.hugh.crm.pojo.ClueActivityRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClueActivityRelationDao {
    List<Activity> getActivityByClueId(String clueId);

    Integer deleteClueActivityById(String id);

    Integer saveClueActivity(ClueActivityRelation clueActivityRelation);
}
