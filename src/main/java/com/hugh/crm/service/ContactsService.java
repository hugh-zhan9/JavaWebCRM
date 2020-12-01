package com.hugh.crm.service;

import com.hugh.crm.pojo.Contacts;

import java.util.List;

public interface ContactsService {
    Integer saveContact(Contacts contact);

    List<Contacts> getAllContacts();

    Contacts getContactsById(String id);
}
