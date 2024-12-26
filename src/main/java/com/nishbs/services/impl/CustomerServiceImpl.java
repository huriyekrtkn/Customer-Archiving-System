package com.nishbs.services.impl;

import com.nishbs.dto.DtoCustomer;
import com.nishbs.dto.DtoCustomerIU;
import com.nishbs.dto.DtoFile;
import com.nishbs.entities.Customer;
import com.nishbs.entities.CustomerFile;
import com.nishbs.exception.CustomerNotFoundException;
import com.nishbs.repository.CustomerRepository;
import com.nishbs.services.ICustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public DtoCustomer addCustomer(DtoCustomerIU dtoCustomerIU) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(dtoCustomerIU, customer);

        if (dtoCustomerIU.getFiles() != null) {
            List<CustomerFile> customerFiles = new ArrayList<>();
            for (DtoFile dtoFile : dtoCustomerIU.getFiles()) {
                CustomerFile customerFile = new CustomerFile();
                BeanUtils.copyProperties(dtoFile, customerFile);
                customerFile.setCustomer(customer);
                customerFiles.add(customerFile);
            }
            customer.setFiles(customerFiles);
        }

        Customer savedCustomer = customerRepository.save(customer);
        DtoCustomer dtoCustomer = new DtoCustomer();
        BeanUtils.copyProperties(savedCustomer, dtoCustomer);

        if (savedCustomer.getFiles() != null && !savedCustomer.getFiles().isEmpty()) {
            List<DtoFile> dtoFiles = new ArrayList<>();
            for (CustomerFile savedFile : savedCustomer.getFiles()) {
                DtoFile dtoFile = new DtoFile();
                BeanUtils.copyProperties(savedFile, dtoFile);
                dtoFiles.add(dtoFile);
            }
            dtoCustomer.setFiles(dtoFiles);
        }
        return dtoCustomer;
    }

    @Override
    public DtoCustomer findCustomerById(Long id) {
        DtoCustomer dtoCustomer = new DtoCustomer();
        Optional<Customer> optional = customerRepository.findById(id);
        if (optional.isPresent()) {
            Customer dbCustomer = optional.get();
            List<CustomerFile> dbFile = optional.get().getFiles();
            BeanUtils.copyProperties(dbCustomer, dtoCustomer);
            if (dbFile != null && !dbFile.isEmpty()){
                for (CustomerFile files : dbFile) {
                    DtoFile dtoFile = new DtoFile();
                    BeanUtils.copyProperties(files, dtoFile);
                    dtoCustomer.getFiles().add(dtoFile);
                }
            }
            return dtoCustomer;
        }
        else {
            throw new CustomerNotFoundException("ID'si " + id + " Olan Müşteri Bulunamadı !");
        }
    }

    @Override
    public List<DtoCustomer> findAllCustomers() {
        List<DtoCustomer> dtoCustomers = new ArrayList<>();
        List<Customer> customers = customerRepository.findAll();

        for (Customer customer : customers) {
            DtoCustomer dtoCustomer = new DtoCustomer();
            BeanUtils.copyProperties(customer,dtoCustomer);

            List<CustomerFile> files = customer.getFiles();

            for (CustomerFile file : files) {
                DtoFile dtoFile = new DtoFile();
                BeanUtils.copyProperties(file,dtoFile);
                dtoCustomer.getFiles().add(dtoFile);
            }
            dtoCustomers.add(dtoCustomer);
        }
        return dtoCustomers;
    }

    @Override
    public void deleteCustomer(Long id) {
        Optional<Customer> optional = customerRepository.findById(id);
        if (optional.isPresent()) {
            customerRepository.delete(optional.get());
        }
        else {
            throw new CustomerNotFoundException("ID'si " + id + " Olan Müşteri Bulunamadı !");
        }
    }

    @Override
    public DtoCustomer updateCustomer(Long id, DtoCustomerIU dtoCustomerIU) {
        Optional<Customer> optional = customerRepository.findById(id);

        if (optional.isPresent()) {
            Customer dbCustomer = optional.get();

            dbCustomer.setFirstName(dtoCustomerIU.getFirstName());
            dbCustomer.setLastName(dtoCustomerIU.getLastName());
            dbCustomer.setDateOfBirth(dtoCustomerIU.getDateOfBirth());
            dbCustomer.setEmail(dtoCustomerIU.getEmail());
            dbCustomer.setPhoneNumber(dtoCustomerIU.getPhoneNumber());

            List<CustomerFile> updatedFiles = new ArrayList<>();
            if (dtoCustomerIU.getFiles() != null) {
                for (DtoFile dtoFile : dtoCustomerIU.getFiles()) {
                    CustomerFile customerFile = new CustomerFile();
                    BeanUtils.copyProperties(dtoFile, customerFile);
                    customerFile.setCustomer(dbCustomer);
                    updatedFiles.add(customerFile);
                }
            }
            dbCustomer.setFiles(updatedFiles);

            Customer updatedCustomer = customerRepository.save(dbCustomer);

            DtoCustomer dtoCustomer = new DtoCustomer();
            BeanUtils.copyProperties(updatedCustomer, dtoCustomer);
            for (CustomerFile dbFile : updatedCustomer.getFiles()) {
                DtoFile dtoFile = new DtoFile();
                BeanUtils.copyProperties(dbFile, dtoFile);
                dtoCustomer.getFiles().add(dtoFile);
            }
            return dtoCustomer;
        }
        else {
            throw new CustomerNotFoundException("ID'si " + id + " Olan Müşteri Bulunamadı !");
        }
    }
}