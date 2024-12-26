package com.nishbs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DtoCustomerIU {

    @Size(min = 2, max = 50)
    @NotEmpty(message = "firstName Alanı Boş Bırakılamaz !")
    private String firstName;

    @Size(min = 2, max = 50)
    @NotEmpty(message = "lastName Alanı Boş Bırakılamaz !")
    private String lastName;

    @NotEmpty(message = "dateOfBirth Alanı Boş Bırakılamaz !")
    private String dateOfBirth;

    @Email(message = "E-mail Formatında Bir Adres Yazınız !")
    @NotEmpty(message = "eMail Alanı Boş Bırakılamaz !")
    private String email;

    @Size(min = 10, max = 10, message = "Telefon Numarasını Başında '0' Olmadan 10 Hane Yazınız !")
    private String phoneNumber;

    private List<DtoFile> files;
}
