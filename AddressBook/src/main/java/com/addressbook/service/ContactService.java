package com.addressbook.service;

import com.addressbook.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    private List<Contact> contacts = new ArrayList<>();

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public Contact addContact(Contact contact) {
        contacts.add(contact);
        return contact;
    }

    public Contact getContactById(int id) {
        return contacts.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public Contact updateContact(int id, Contact newContact) {
        for (Contact c : contacts) {
            if (c.getId() == id) {
                c.setName(newContact.getName());
                c.setEmail(newContact.getEmail());
                c.setPhone(newContact.getPhone());
                return c;
            }
        }
        return null;
    }

    public boolean deleteContact(int id) {
        return contacts.removeIf(c -> c.getId() == id);
    }
}
