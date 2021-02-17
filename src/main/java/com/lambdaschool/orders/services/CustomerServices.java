package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.views.CustomerOrderCounts;

import java.util.List;

public interface CustomerServices
{
    // READ METHODS (GET METHODS)

    List<Customer> findAllCustomerOrders(); //get all customers by order method

    Customer findCustomerById(long id); // get customer by id

    Customer findCustomerByCustname(String name);   // get customer by name

    List<Customer> findCustomerByLikeName(String subname); //get customer by like name

    List<CustomerOrderCounts> getCustomerOrderCounts(); //get order counts for reach customer

    //CREATE, UPDATE, DELETE METHODS

    // Create
    // Using POST method header
    // Use save for PUT method on updating evryuthign with regards to customer
    Customer save(Customer customer);       // create

    //Using PATCH method header to only part of customer
    Customer update(long id, Customer customer);        // update

    void deleteById(long id);       // delete

    void deleteAll();               // delete all
}
