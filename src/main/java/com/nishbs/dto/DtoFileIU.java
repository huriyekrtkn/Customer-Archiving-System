package com.nishbs.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DtoFileIU {

    @NotEmpty(message = "Dosya Adı Boş Olamaz !")
    private String fileName;

    @NotEmpty(message = "Dosya Tipi Boş Olamaz !")
    private String fileType;

    private DtoCustomerIU customer;
}