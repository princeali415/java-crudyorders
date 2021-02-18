package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.OrdersRepository;
import com.lambdaschool.orders.views.CustomerOrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service(value = "orderServices")
public class OrderServiceImpl implements OrderServices
{
    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Order findOrderById(long ordnum)
    {
        Order order = ordersRepository.findById(ordnum)
            .orElseThrow(()-> new EntityNotFoundException("Order "+ ordnum + " Not Found")); //handles if non-existent
        return order;
    }

    @Transactional
    @Override
    public Order save(Order tempOrder)
    {
        Order newOrder = new Order();

        //PUT or POST
        if(tempOrder.getOrdnum() != 0){
            ordersRepository.findById(tempOrder.getOrdnum())
                .orElseThrow(()-> new EntityNotFoundException("Order " + tempOrder.getOrdnum() + " Not Found"));
            newOrder.setOrdnum(tempOrder.getOrdnum());
        }

        newOrder.setOrdamount(tempOrder.getOrdamount());
        newOrder.setAdvanceamount(tempOrder.getAdvanceamount());
        newOrder.setCustomer(tempOrder.getCustomer());
        newOrder.setOrderdescription(tempOrder.getOrderdescription());

        newOrder.getPayments().clear();
        for(Payment p : tempOrder.getPayments()){
            Payment newPayment = new Payment();
            newPayment.setType(p.getType());

            newOrder.getPayments().add(newPayment);
        }

        return ordersRepository.save(newOrder);
    }



    @Transactional
    @Override
    public void deleteById(long id)
    {
        if(ordersRepository.findById(id).isPresent()){
            ordersRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Order " + id + " Not Found");
        }
    }

    @Transactional
    @Override
    public void deleteAll()
    {
        ordersRepository.deleteAll();
    }
}

