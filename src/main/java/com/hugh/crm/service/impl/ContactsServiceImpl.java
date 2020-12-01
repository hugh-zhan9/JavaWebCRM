package com.hugh.crm.service.impl;

import com.hugh.crm.dao.ContactsDao;
import com.hugh.crm.pojo.Contacts;
import com.hugh.crm.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    ContactsDao contactsDao;

    @Override
    public Integer saveContact(Contacts contact) {
        contactsDao.saveContact(contact);
        return 1;
    }

    @Override
    public List<Contacts> getAllContacts() {
        List<Contacts> contacts = contactsDao.queryAllContacts();
        return contacts;
    }

    @Override
    public Contacts getContactsById(String id) {
        Contacts contact = contactsDao.getContactsById(id);
        return contact;
    }
}
