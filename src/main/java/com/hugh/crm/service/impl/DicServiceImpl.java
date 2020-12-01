package com.hugh.crm.service.impl;

import com.hugh.crm.dao.DicTypeDao;
import com.hugh.crm.dao.DicValueDao;
import com.hugh.crm.pojo.DicType;
import com.hugh.crm.pojo.DicValue;
import com.hugh.crm.service.DicService;
import com.hugh.crm.util.MybatisUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DicServiceImpl implements DicService {
    /*
    由于监听器不是由Spring创建的，所以这里自动注入会为null
    @Autowired
    DicTypeDao dicTypeDao;
    @Autowired
    DicValueDao dicValueDao;
    */
    private DicTypeDao dicTypeDao = MybatisUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = MybatisUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getDicValue() {
        Map<String,List<DicValue>> dicMap = new HashMap<String, List<DicValue>>();

        List<DicType> dicTypeList = dicTypeDao.getAllDicType();
        for(DicType dicType : dicTypeList){
            String typeCode = dicType.getCode();
            // 取出的是内存值
            dicMap.put(typeCode,dicValueDao.getValueByTypeCode(typeCode));
        }
        return dicMap;
    }
}
