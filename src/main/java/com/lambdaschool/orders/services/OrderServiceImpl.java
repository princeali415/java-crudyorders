package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
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

    @Transactional
    @Override
    public Order save(Order order)
    {
        return ordersRepository.save(order);
    }

    @Override
    public Order findOrderById(long ordnum)
    {
        Order order = ordersRepository.findById(ordnum)
            .orElseThrow(()-> new EntityNotFoundException("Order "+ ordnum + " Not Found")); //handles if non-existent
        return order;
    }

}

