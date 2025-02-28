package com.addressbook.service;

import com.addressbook.dto.ContactDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    private final List<ContactDTO> contacts = new ArrayList<>();
    private long nextId = 1; // Auto-increment ID

    // GET all contacts
    public List<ContactDTO> getAllContacts() {
        return contacts;
    }

    // GET contact by ID
    public Optional<ContactDTO> getContactById(Long id) {
        return contacts.stream().filter(contact -> contact.getId().equals(id)).findFirst();

    }

    // POST - Add a new contact
    public ContactDTO addContact(ContactDTO contactDTO) {
        contactDTO.setId(nextId++); // Assign unique ID
        contacts.add(contactDTO);
        return contactDTO;
    }

    // PUT - Update contact by ID
    public Optional<ContactDTO> updateContact(Long id, ContactDTO updatedContact) {
        return getContactById(id).map(existingContact -> {
            existingContact.setName(updatedContact.getName());
            existingContact.setEmail(updatedContact.getEmail());
            existingContact.setPhone(updatedContact.getPhone());
            return existingContact;
        });
    }

    // DELETE contact by ID
    public boolean deleteContact(Long id) {
        return contacts.removeIf(contact -> contact.getId().equals(id));
    }
}
