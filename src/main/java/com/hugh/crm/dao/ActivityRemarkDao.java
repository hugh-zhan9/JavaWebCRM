package com.hugh.crm.dao;

import com.hugh.crm.pojo.ActivityRemark;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRemarkDao {
    Integer deleteActivityRemark(String id);

    Integer getActivityRemarkCountById(String id);

    List<ActivityRemark> getActivityRemarkById(String id);

    Integer saveActivityRemarkContent(ActivityRemark activityRemark);
}
