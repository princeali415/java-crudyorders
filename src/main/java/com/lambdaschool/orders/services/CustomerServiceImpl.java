package com.lambdaschool.orders.services;


import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import com.lambdaschool.orders.views.CustomerOrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerServices")
public class CustomerServiceImpl implements CustomerServices
{
    @Autowired
    private CustomersRepository customersRepository;


    // GET Method implementation

    @Override
    public List<Customer> findAllCustomerOrders()
    {
        List<Customer> customerOrdersList = new ArrayList<>();
        customersRepository.findAll().iterator().forEachRemaining(customerOrdersList::add);

        return customerOrdersList;
    }

    @Override
    public Customer findCustomerById(long id)
    {
        Customer customer = customersRepository.findById(id)
            .orElseThrow(()-> new EntityNotFoundException("Customer "+ id +" Not Found"));  //handles if id doesnt exist
        return customer;
    }

    @Override
    public Customer findCustomerByCustname(String name)
    {
        Customer customer = customersRepository.findCustomerByCustname(name);
        if(customer == null) {
            throw new EntityNotFoundException("Customer "+ name +" Not Found");
        }
        return customer;
    }

    @Override
    public List<Customer> findCustomerByLikeName(String subname)
    {
        List<Customer> customerList = customersRepository.findCustomerByCustnameContainingIgnoringCase(subname);
        return customerList;
    }

    @Override
    public List<CustomerOrderCounts> getCustomerOrderCounts()
    {
        List<CustomerOrderCounts> customerOrderCount = customersRepository.getCustomerOrderCounts();
        return customerOrderCount;
    }

    // POST, PUT, PATCH, DELETE Method implementations
    @Autowired
    private PaymentRepository paymentRepository;    // need this for payments

    @Transactional                              // we use transactional annotation beacuse they are modifying things in
    @Override                                   // in the database
    public Customer save(Customer tempCustomer) // customer argument (which will be a JSON obj is temporary and will
    {                                           // disappear from memory once method is executed and whatever is related to it
        Customer newCustomer = new Customer();

        // PUT or a POST
        if (tempCustomer.getCustcode() != 0 ){     // conditional that syas if we dont have a customer id then
            //PUT                                  // we will do a PUT method. If Id is NOT null we do POST
            customersRepository.findById(tempCustomer.getCustcode())
                .orElseThrow(()-> new EntityNotFoundException("Customer " + tempCustomer.getCustcode()+ " Not Found"));
            newCustomer.setCustcode(tempCustomer.getCustcode());
        }

        newCustomer.setPhone(tempCustomer.getPhone());
        newCustomer.setCustcity(tempCustomer.getCustcity());
        newCustomer.setCustcountry(tempCustomer.getCustcountry());
        newCustomer.setCustname(tempCustomer.getCustname());
        newCustomer.setGrade(tempCustomer.getGrade());
        newCustomer.setOpeningamt(tempCustomer.getOpeningamt());
        newCustomer.setOutstandingamt(tempCustomer.getOutstandingamt());
        newCustomer.setPaymentamt(tempCustomer.getPaymentamt());
        newCustomer.setRecieveamt(tempCustomer.getRecieveamt());
        newCustomer.setWorkingarea(tempCustomer.getWorkingarea());
        newCustomer.setAgent(tempCustomer.getAgent());

        // newCustomer.setOrders(tempCustomer.getOrders()); WRONG// need to make a deep copy because agent is a obj or List, if we dont
                                                               //we pass by reference, which we dont want because it will
                                                               // throw error
        // RIGHT way to make new list
        newCustomer.getOrders().clear();
        for (Order o : tempCustomer.getOrders()){
            Order newOrder = new Order();
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setCustomer(o.getCustomer());
            newOrder.setOrderdescription(o.getOrderdescription());

            for(Payment p : newOrder.getPayments()){            // right way to make new obj
                Payment newPayment = paymentRepository.findById(p.getPaymentid())
                    .orElseThrow(()-> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
                newOrder.getPayments().add(newPayment);
            }
            newCustomer.getOrders().add(newOrder);
        }
//        double ordamount,
//        double advanceamount,
//        Customer customer,
//        String orderdescription
        //        payments hashmap :( have to do nested for each loop

        return customersRepository.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer update(long id, Customer tempCustomer)          //1. copy and past from save method
    {                                                               //2.rename newcustomer to updateCustomer (easy to tell diffrence between update and save
        Customer updateCustomer = customersRepository.findById(id)  //3. update customer to equal instance of id (line131 to 132
                .orElseThrow(()-> new EntityNotFoundException("Customer " + id + " Not Found"));

        //4. if statements for fields to update based on if field is null or not (because this is a patch)
        // NOTE* if int or float have to create public boolean in model for customer,(line 30-31 declaring the boolean false)
        // line 156 is setting boolean to true if setter is used. (hasvalueforgetopeningamt example) repeat for other ints
        if(tempCustomer.getPhone() != null) { updateCustomer.setPhone(tempCustomer.getPhone()); }
        if(tempCustomer.getCustcity() != null) { updateCustomer.setCustcity(tempCustomer.getCustcity()); }
        if(tempCustomer.getCustcountry() != null) { updateCustomer.setCustcountry(tempCustomer.getCustcountry()); }
        if(tempCustomer.getCustname() != null) { updateCustomer.setCustname(tempCustomer.getCustname()); }
        if(tempCustomer.getGrade() != null) { updateCustomer.setGrade(tempCustomer.getGrade()); }
        if(tempCustomer.hasvalueforgetOpeningamt) { updateCustomer.setOpeningamt(tempCustomer.getOpeningamt()); }
        if(tempCustomer.hasvalueforgetOutstandingamt) { updateCustomer.setOutstandingamt(tempCustomer.getOutstandingamt()); }
        if(tempCustomer.hasvalueforgetPaymentamt) { updateCustomer.setPaymentamt(tempCustomer.getPaymentamt()); }
        if(tempCustomer.hasvalueforgetRecieveamt) { updateCustomer.setRecieveamt(tempCustomer.getRecieveamt()); }
        if(tempCustomer.getWorkingarea() != null) { updateCustomer.setWorkingarea(tempCustomer.getWorkingarea()); }
        if(tempCustomer.getAgent() != null) { updateCustomer.setAgent(tempCustomer.getAgent()); }

        // 5. add if statement to check if there are any orders, ifd then we patch
        if(tempCustomer.getOrders().size() > 0) {
            updateCustomer.getOrders().clear();
            for (Order o : tempCustomer.getOrders()) {
                Order newOrder = new Order();
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setCustomer(o.getCustomer());
                newOrder.setOrderdescription(o.getOrderdescription());
                // add if statement for the nested for payments within order
                if (newOrder.getPayments().size() > 0) {
                    for (Payment p : newOrder.getPayments()) {            // right way to make new obj
                        Payment newPayment = paymentRepository.findById(p.getPaymentid())
                            .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
                        newOrder.getPayments().add(newPayment);
                    }
                    updateCustomer.getOrders().add(newOrder);
                }
            }
        }

        //        double ordamount,
        //        double advanceamount,
        //        Customer customer,
        //        String orderdescription
        //        payments hashmap :( have to do nested for each loop

        return customersRepository.save(updateCustomer);
    }

    @Transactional
    @Override
    public void deleteById(long id)
    {
        if (customersRepository.findById(id).isPresent()){
            customersRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Customer " + id + " Not Found");
        }
    }

    @Transactional
    @Override
    public void deleteAll()
    {
        customersRepository.deleteAll();
    }
}
