package com.addressbook.controller;

import com.addressbook.model.Contact;
import com.addressbook.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }
    @PostMapping
    public Contact addContact(@RequestBody Contact contact) {
        return contactService.addContact(contact);
    }

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable int id) {
        return contactService.getContactById(id);
    }
    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable int id, @RequestBody Contact contact) {
        return contactService.updateContact(id, contact);
    }
    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable int id) {
        return contactService.deleteContact(id) ? "Deleted Successfully" : "Contact Not Found";
    }
}
