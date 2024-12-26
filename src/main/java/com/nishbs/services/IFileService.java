package com.nishbs.services;

import com.nishbs.dto.DtoFile;
import com.nishbs.dto.DtoFileIU;

import java.util.List;

public interface IFileService {

    public DtoFile addFileToCustomer(Long customerId, DtoFileIU dtoFileIU);

    public List<DtoFile> getAllFilesByCustomerId(Long customerId);

    public DtoFile getFileByCustomerIdAndFileId(Long customerId, Long fileId);

    public void deleteAllFilesByCustomerId(Long customerId);

    public void deleteFileByCustomerIdAndFileId(Long customerId, Long fileId);

    public DtoFile updateFileByCustomerIdAndFileId(Long customerId, Long fileId, DtoFileIU dtoFileIU);
}