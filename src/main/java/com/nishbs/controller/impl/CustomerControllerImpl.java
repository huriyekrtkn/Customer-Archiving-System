package com.nishbs.controller.impl;

import com.nishbs.controller.ICustomerController;
import com.nishbs.dto.DtoCustomer;
import com.nishbs.dto.DtoCustomerIU;
import com.nishbs.services.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer-archive/customer")
public class CustomerControllerImpl implements ICustomerController {

    @Autowired
    private ICustomerService customerService;

    @PostMapping(path = "/save")
    @Override
    public DtoCustomer addCustomer(@RequestBody @Valid DtoCustomerIU dtoCustomerIU) {
        return customerService.addCustomer(dtoCustomerIU);
    }

    @GetMapping(path = "/list/{id}")
    @Override
    public DtoCustomer findCustomerById(@PathVariable(name = "id") Long id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping(path = "/list")
    @Override
    public List<DtoCustomer> findAllCustomers() {
        return customerService.findAllCustomers();
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public String deleteCustomer(@PathVariable(name = "id") Long id) {
        customerService.deleteCustomer(id);
        return "ID'si " + id + " Olan Müşteri Başarıyla Silindi !";
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public DtoCustomer updateCustomer(@PathVariable(name = "id") Long id, @RequestBody DtoCustomerIU dtoCustomerIU) {
        return customerService.updateCustomer(id, dtoCustomerIU);
    }
}