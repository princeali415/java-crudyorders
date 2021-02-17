package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.views.CustomerOrderCounts;

import java.util.List;

public interface CustomerServices
{
    public Customer save(Customer customer);

    List<Customer> findAllCustomerOrders(); //get all customers by order method

    Customer findCustomerById(long id); // get customer by id

    Customer findCustomerByCustname(String name);   // get customer by name

    List<Customer> findCustomerByLikeName(String subname); //get customer by like name

    List<CustomerOrderCounts> getCustomerOrderCounts(); //get order counts for reach customer
}
