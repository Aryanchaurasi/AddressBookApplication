package com.addressbook.dto;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    public ContactDTO(String name,String email,String phone)
    {
        this.name=name;
        this.email=email;
        this.phone=phone;
    }
    private Long id; // Add this line
    private String name;
    private String email;
    private String phone;
}