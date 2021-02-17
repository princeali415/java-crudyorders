package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerServices;
import com.lambdaschool.orders.views.CustomerOrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController
{
    @Autowired
    private CustomerServices customerServices;

    // GET requests

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

    // DELETE REQUESTS

    //http://localhost:2019/customers/customer/{custcode}
    @DeleteMapping(value = "/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode){
        customerServices.deleteById(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // POST REQUESTS

    //http://localhost:2019/customers/customer
    @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer customer){
        customer.setCustcode(0);
        customer = customerServices.save(customer);

        HttpHeaders responseHeader = new HttpHeaders();         // goes to new customer created
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{custcode}")
            .buildAndExpand(customer.getCustcode())
            .toUri();
        responseHeader.setLocation(newCustomerURI);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    //PUT REQUEST

    //http://localhost:2019/customers/customer/{custcode}

    @PutMapping(value = "/customer/{custcode}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> replaceCustomer(@Valid @RequestBody Customer customer, @PathVariable long custcode){
        customer.setCustcode(custcode);
        Customer newCustomer = customerServices.save(customer);

        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }

    //PATCH REQUEST

    //http://localhost:2019/customers/customer/{custcode}

    @PatchMapping(value = "/customer/{custcode}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable long custcode){

        Customer newCustomer = customerServices.update(custcode, customer);

        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }
}
