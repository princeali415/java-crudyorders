package com.lambdaschool.orders.views;

public interface CustomerOrderCounts    // need viws to create 'view' of the json object fields
{
     String getCustname();         // name shoul be same as on SQL query

     int getCount();
}
