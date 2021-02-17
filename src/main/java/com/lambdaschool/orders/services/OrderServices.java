package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;


import java.util.List;

public interface OrderServices
{
    public Order save(Order order);

    Order findOrderById(long ordnum);

}
