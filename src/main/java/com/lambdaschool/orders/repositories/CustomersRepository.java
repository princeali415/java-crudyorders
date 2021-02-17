package com.lambdaschool.orders.repositories;

import com.lambdaschool.orders.models.Customer;

import com.lambdaschool.orders.views.CustomerOrderCounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long>
{
    Customer findCustomerByCustname(String name); // need ot make custom place in rep.

    List<Customer> findCustomerByCustnameContainingIgnoringCase(String subname);   // need to make custom method in rep.

    @Query(value = "SELECT c.custname, count(o.ordnum) AS count " +     // SQL query for order counts
        "FROM customers c LEFT JOIN orders o " +
        "ON c.custcode = o.custcode " +
        "GROUP BY c.custcode " +
        "ORDER BY count DESC", nativeQuery = true)
    List<CustomerOrderCounts> getCustomerOrderCounts();
}
