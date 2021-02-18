package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController
{
    @Autowired
    private OrderServices orderServices;

    //GET REQUEST

    //http://localhost:2019/orders/order/{ordnum}
    @GetMapping(value = "/order/{ordernum}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long ordernum)
    {
        Order order = orderServices.findOrderById(ordernum);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //DELETE REQUESTS

    //http://localhost:2019/orders/order/{ordnum}
    @DeleteMapping(value = "/order/{ordnum}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long ordnum){
        orderServices.deleteById(ordnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // POST REQUEST

    //http://localhost:2019/orders/order
    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order order){
        order.setOrdnum(0);
        order = orderServices.save(order);

        HttpHeaders responseHeader = new HttpHeaders();         // goes to new customer created
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{ordnum}")
            .buildAndExpand(order.getOrdnum())
            .toUri();
        responseHeader.setLocation(newOrderURI);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

}
