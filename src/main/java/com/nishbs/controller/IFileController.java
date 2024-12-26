package com.nishbs.controller;

import com.nishbs.dto.DtoFile;
import com.nishbs.dto.DtoFileIU;

import java.util.List;

public interface IFileController {

    public DtoFile addFileToCustomer(Long customerId, DtoFileIU dtoFileIU);

    public List<DtoFile> getAllFilesByCustomerId(Long customerId);

    public DtoFile getFileByCustomerIdAndFileId(Long customerId, Long fileId);

    public String deleteAllFilesByCustomerId(Long customerId);

    public String deleteFileByCustomerIdAndFileId(Long customerId, Long fileId);

    public DtoFile updateFileByCustomerIdAndFileId(Long customerId, Long fileId, DtoFileIU dtoFileIU);
}