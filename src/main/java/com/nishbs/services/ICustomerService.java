package com.nishbs.services;

import com.nishbs.dto.DtoCustomer;
import com.nishbs.dto.DtoCustomerIU;

import java.util.List;

public interface ICustomerService {

    public DtoCustomer addCustomer(DtoCustomerIU dtoCustomerIU);

    public DtoCustomer findCustomerById(Long id);

    public List<DtoCustomer> findAllCustomers();

    public void deleteCustomer(Long id);

    public DtoCustomer updateCustomer(Long id, DtoCustomerIU dtoCustomerIU);
}