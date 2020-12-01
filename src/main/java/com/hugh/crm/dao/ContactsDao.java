package com.hugh.crm.dao;

import com.hugh.crm.pojo.Contacts;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsDao {
    Integer saveContact (Contacts contact);

    List<Contacts> queryAllContacts();

    Contacts getContactsById(String id);

    Integer saveContactsActivity(@Param("id") String id, @Param("contactsId") String contactId, @Param("activityId") String activityId);

    List<Contacts> getBoundContactsById(String id);

    List<Contacts> getAllContacts();
}
