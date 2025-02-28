package com.addressbook.controller;

import com.addressbook.dto.ContactDTO;
import com.addressbook.model.Contact;
import com.addressbook.repository.ContactRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Convert Entity to DTO
    private ContactDTO convertToDTO(Contact contact) {
        return new ContactDTO(contact.getName(), contact.getEmail(), contact.getPhone());
    }

    // GET ALL CONTACTS
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        List<ContactDTO> contacts = contactRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactRepository.findById(id);

        if (contact.isPresent()) {
            return ResponseEntity.ok(convertToDTO(contact.get())); // Returns ContactDTO
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " Contact with ID " + id + " not found!");
        }
    }
    //  CREATE NEW CONTACT (AUTO-ID)
    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = new Contact(null, contactDTO.getName(), contactDTO.getEmail(), contactDTO.getPhone());
        Contact savedContact = contactRepository.save(contact);
        return ResponseEntity.ok(" Contact Added Successfully! ID: " + savedContact.getId());
    }

    //  UPDATE CONTACT BY ID (AUTO-ID)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @RequestBody ContactDTO updatedContactDTO) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setName(updatedContactDTO.getName());
                    contact.setEmail(updatedContactDTO.getEmail());
                    contact.setPhone(updatedContactDTO.getPhone());
                    Contact updatedContact = contactRepository.save(contact);
                    return ResponseEntity.ok(" Contact Updated Successfully! ID: " + updatedContact.getId());
                })
                .orElseGet(() -> ResponseEntity.badRequest().body(" Contact Not Found!"));
    }

    //  DELETE CONTACT BY ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return ResponseEntity.ok(" Contact Deleted Successfully! ID: " + id);
        } else {
            return ResponseEntity.badRequest().body(" Contact Not Found!");
        }
    }
}
