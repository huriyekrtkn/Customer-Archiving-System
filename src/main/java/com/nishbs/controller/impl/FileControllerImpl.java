package com.nishbs.controller.impl;

import com.nishbs.controller.IFileController;
import com.nishbs.dto.DtoFile;
import com.nishbs.dto.DtoFileIU;
import com.nishbs.services.IFileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer-archive/customer")
public class FileControllerImpl implements IFileController {

    @Autowired
    private IFileService fileService;

    @PostMapping(path = "/add-file/{customerId}")
    @Override
    public DtoFile addFileToCustomer(@PathVariable Long customerId, @RequestBody @Valid DtoFileIU dtoFileIU) {
        return fileService.addFileToCustomer(customerId, dtoFileIU);
    }

    @GetMapping(path = "/list-files/{customerId}")
    @Override
    public List<DtoFile> getAllFilesByCustomerId(@PathVariable Long customerId) {
        return fileService.getAllFilesByCustomerId(customerId);
    }

    @GetMapping(path = "/list-files/{customerId}/{fileId}")
    @Override
    public DtoFile getFileByCustomerIdAndFileId(@PathVariable Long customerId, @PathVariable Long fileId) {
        return fileService.getFileByCustomerIdAndFileId(customerId, fileId);
    }

    @DeleteMapping(path = "/delete-all-files/{customerId}")
    @Override
    public String deleteAllFilesByCustomerId(@PathVariable Long customerId) {
        fileService.deleteAllFilesByCustomerId(customerId);
        return "ID'si " + customerId + " Olan Müşterinin Tüm Dosyaları Başarıyla Silindi !";
    }

    @DeleteMapping(path = "/delete-file/{customerId}/{fileId}")
    @Override
    public String deleteFileByCustomerIdAndFileId(@PathVariable Long customerId, @PathVariable Long fileId) {
        fileService.deleteFileByCustomerIdAndFileId(customerId, fileId);
        return "ID'si " + customerId + " Olan Müşterinin ID'si " + fileId + " Olan Dosyası Başarıyla Silindi !";
    }

    @PutMapping(path = "/update-file/{customerId}/{fileId}")
    @Override
    public DtoFile updateFileByCustomerIdAndFileId(@PathVariable Long customerId, @PathVariable Long fileId, @RequestBody @Valid DtoFileIU dtoFileIU) {
        return fileService.updateFileByCustomerIdAndFileId(customerId, fileId, dtoFileIU);
    }
}