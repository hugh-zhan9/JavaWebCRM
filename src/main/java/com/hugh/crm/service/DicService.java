package com.hugh.crm.service;

import com.hugh.crm.pojo.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getDicValue();
}
