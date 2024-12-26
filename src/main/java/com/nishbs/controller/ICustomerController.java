package com.nishbs.controller;

import com.nishbs.dto.DtoCustomer;
import com.nishbs.dto.DtoCustomerIU;

import java.util.List;

public interface ICustomerController {

    public DtoCustomer addCustomer(DtoCustomerIU dtoCustomerIU);

    public DtoCustomer findCustomerById(Long id);

    public List<DtoCustomer> findAllCustomers();

    public String deleteCustomer(Long id);

    public DtoCustomer updateCustomer(Long id, DtoCustomerIU dtoCustomerIU);
}