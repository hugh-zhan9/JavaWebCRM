package com.hugh.crm.service.impl;

import com.hugh.crm.dao.ClueDao;
import com.hugh.crm.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    ClueDao clueDao;
}
