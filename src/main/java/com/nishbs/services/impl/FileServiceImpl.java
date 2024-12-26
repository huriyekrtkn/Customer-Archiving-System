package com.nishbs.services.impl;

import com.nishbs.dto.DtoCustomer;
import com.nishbs.dto.DtoFile;
import com.nishbs.dto.DtoFileIU;
import com.nishbs.entities.Customer;
import com.nishbs.entities.CustomerFile;
import com.nishbs.exception.CustomerNotFoundException;
import com.nishbs.exception.FileNotFoundException;
import com.nishbs.repository.CustomerRepository;
import com.nishbs.repository.FileRepository;
import com.nishbs.services.IFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FileRepository fileRepository;


    @Override
    public DtoFile addFileToCustomer(Long customerId, DtoFileIU dtoFileIU) {
        Optional<Customer> optional = customerRepository.findById(customerId);
        if (optional.isPresent()) {
            Customer customer = optional.get();

            if (customer.getFiles() == null) {
                customer.setFiles(new ArrayList<>());
            }

            CustomerFile customerFile = new CustomerFile();
            customerFile.setFileName(dtoFileIU.getFileName());
            customerFile.setFileType(dtoFileIU.getFileType());
            customerFile.setCustomer(customer);

            customer.getFiles().add(customerFile);
            fileRepository.save(customerFile);
            customerRepository.save(customer);

            DtoFile dtoFile = new DtoFile();
            BeanUtils.copyProperties(customerFile, dtoFile);

            return dtoFile;
        }
        else {
            throw new CustomerNotFoundException("ID'si " + customerId + " Olan Müşteri Bulunamadı !");
        }
    }

    @Override
    public List<DtoFile> getAllFilesByCustomerId(Long customerId) {
        Optional<Customer> optional = customerRepository.findById(customerId);

        if (optional.isPresent()) {
            Customer customer = optional.get();

            List<CustomerFile> files = customer.getFiles();

            if (files == null || files.isEmpty()) {
                throw new FileNotFoundException("ID'si " + customerId + " Olan Müşterinin Hiç Dosyası Yok !");
            }

            List<DtoFile> dtoFiles = new ArrayList<>();

            for (CustomerFile file : files) {
                DtoFile dtoFile = new DtoFile();
                BeanUtils.copyProperties(file, dtoFile);
                dtoFiles.add(dtoFile);
            }

            return dtoFiles;
        }
        else {
            throw new CustomerNotFoundException("ID'si " + customerId + " Olan Müşteri Bulunamadı !");
        }
    }

    @Override
    public DtoFile getFileByCustomerIdAndFileId(Long customerId, Long fileId) {
        Optional<Customer> optional = customerRepository.findById(customerId);

        if (optional.isPresent()) {
            Customer customer = optional.get();
            List<CustomerFile> files = customer.getFiles();

            if (files != null && !files.isEmpty()) {
                for (CustomerFile file : files) {
                    if (file.getId().equals(fileId)) {
                        DtoFile dtoFile = new DtoFile();
                        BeanUtils.copyProperties(file, dtoFile);

                        DtoCustomer dtoCustomer = new DtoCustomer();
                        dtoCustomer.setId(customer.getId());
                        dtoCustomer.setFirstName(customer.getFirstName());
                        dtoCustomer.setLastName(customer.getLastName());
                        dtoFile.setCustomer(dtoCustomer);
                        return dtoFile;
                    }
                }
                throw new FileNotFoundException("ID'si " + customerId + " Olan Müşterinin ID'si " + fileId + " Olan Dosyası Bulunamadı !");
            }
            throw new FileNotFoundException("ID'si " + customerId + " Olan Müşterinin Hiç Dosyası Yok !");
        }
        else {
            throw new CustomerNotFoundException("ID'si " + customerId + " Olan Müşteri Bulunamadı !");
        }
    }

    @Override
    public void deleteAllFilesByCustomerId(Long customerId) {
        Optional<Customer> optional = customerRepository.findById(customerId);

        if (optional.isPresent()) {
            Customer customer = optional.get();
            List<CustomerFile> files = customer.getFiles();

            if (files == null || files.isEmpty()) {
                throw new FileNotFoundException("ID'si " + customerId + " Olan Müşterinin Hiç Dosyası Yok !");
            }

            fileRepository.deleteAllInBatch(files);
            customer.setFiles(new ArrayList<>());
            customerRepository.save(customer);
        }
        else {
            throw new CustomerNotFoundException("ID'si " + customerId + " Olan Müşteri Bulunamadı !");
        }
    }

    @Override
    public void deleteFileByCustomerIdAndFileId(Long customerId, Long fileId) {
        Optional<Customer> optional = customerRepository.findById(customerId);

        if (optional.isPresent()) {
            Customer customer = optional.get();
            List<CustomerFile> files = customer.getFiles();

            if (files != null && !files.isEmpty()) {
                for (CustomerFile file : new ArrayList<>(files)) {
                    if (file.getId().equals(fileId)) {
                        files.remove(file);
                        fileRepository.delete(file);
                        customerRepository.save(customer);
                        return;
                    }
                }
                throw new FileNotFoundException("ID'si " + customerId + " Olan Müşterinin ID'si " + fileId + " Olan Dosyası Bulunamadı !");
            }
            throw new FileNotFoundException("ID'si " + customerId + " Olan Müşterinin Hiç Dosyası Yok !");
        }
        else {
            throw new CustomerNotFoundException("ID'si " + customerId + " Olan Müşteri Bulunamadı !");
        }
    }

    @Override
    public DtoFile updateFileByCustomerIdAndFileId(Long customerId, Long fileId, DtoFileIU dtoFileIU) {
        Optional<Customer> optional = customerRepository.findById(customerId);

        if (optional.isPresent()) {
            Customer customer = optional.get();
            List<CustomerFile> files = customer.getFiles();

            if (files != null && !files.isEmpty()) {
                for (CustomerFile file : files) {
                    if (file.getId().equals(fileId)) {
                        file.setFileName(dtoFileIU.getFileName());
                        file.setFileType(dtoFileIU.getFileType());

                        fileRepository.save(file);
                        customerRepository.save(customer);

                        DtoFile dtoFile = new DtoFile();
                        dtoFile.setId(file.getId());
                        dtoFile.setFileName(file.getFileName());
                        dtoFile.setFileType(file.getFileType());
                        return dtoFile;
                    }
                }
                throw new FileNotFoundException("ID'si " + customerId + " Olan Müşterinin ID'si " + fileId + " Olan Dosyası Bulunamadı !");
            }
            throw new FileNotFoundException("ID'si " + customerId + " Olan Müşterinin Hiç Dosyası Yok !");
        }
        else {
            throw new CustomerNotFoundException("ID'si " + customerId + " Olan Müşteri Bulunamadı !");
        }
    }
}