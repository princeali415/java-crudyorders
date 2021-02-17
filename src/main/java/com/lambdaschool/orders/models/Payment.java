package com.lambdaschool.orders.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payments")
public class Payment
{
    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long paymentid;

    @Column(nullable = false, unique = true)
    private String type;

    // relationships with orders payments which is many to many
    @ManyToMany
    @JoinTable(name = "orderspayments", joinColumns = @JoinColumn(name = "paymentid"), inverseJoinColumns = @JoinColumn(name = "ordnum"))
    private Set<Order> orders = new HashSet<>();

    //constructors

    public Payment()
    {
        // required by JPA
    }

    public Payment(String type)
    {
        this.type = type;
    }

    // GETTERS AND SETTERS
    public long getPaymentid()
    {
        return paymentid;
    }

    public void setPaymentid(long paymentid)
    {
        this.paymentid = paymentid;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Set<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(Set<Order> orders)
    {
        this.orders = orders;
    }


}
