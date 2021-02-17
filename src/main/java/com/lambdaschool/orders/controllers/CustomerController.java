package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerServices;
import com.lambdaschool.orders.views.CustomerOrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController
{
    @Autowired
    private CustomerServices customerServices;

    //http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomerOrders()
    {
        List<Customer> customerOrdersList = customerServices.findAllCustomerOrders();
        return new ResponseEntity<>(customerOrdersList, HttpStatus.OK);
    }


    //http://localhost:2019/customers/customer/{custcode}
    @GetMapping(value = "/customer/{custcode}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable long custcode)
    {
        Customer customer = customerServices.findCustomerById(custcode);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    //http://localhost:2019/customers/name/{custname}
    @GetMapping(value = "/name/{custname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByName(@PathVariable String custname)
    {
        Customer customer = customerServices.findCustomerByCustname(custname);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }


    //http://localhost:2019/customers/namelike/{subname}
    @GetMapping(value = "/namelike/{subname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByLikeName(@PathVariable String subname)
    {
        List<Customer> customerList = customerServices.findCustomerByLikeName(subname);
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    //http://localhost:2019/customers/orders/count               // endpoint that lists all orders grouped by customer
    @GetMapping(value = "/orders/count", produces = "application/json")
    public ResponseEntity<?> getCustomerOrderCounts()
    {
        List<CustomerOrderCounts> orderCounts = customerServices.getCustomerOrderCounts();
        return new ResponseEntity<>(orderCounts, HttpStatus.OK);
    }
}
