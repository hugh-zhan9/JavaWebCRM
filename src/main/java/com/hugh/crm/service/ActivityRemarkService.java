package com.hugh.crm.service;

import com.hugh.crm.pojo.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    List<ActivityRemark> getActivityRemarkById(String id);

    Boolean saveNoteContent(ActivityRemark activityRemark);

    Boolean deleteActivityAndRemark(String activityId);
}
