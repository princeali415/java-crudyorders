package com.lambdaschool.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@JsonIgnoreProperties(value =
    {"hasvalueforgetOpeningamt", "hasvalueforgetOutstandingamt",
        "hasvalueforgetPaymentamt", "hasvalueforgetRecieveamt"})   //ignores this val in database only need it for patch method
public class Customer
{
    //fields
    // id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long custcode;

    private String custcity;

    private String custcountry;

    private String custname;

    private String grade;

    private double openingamt;

    @Transient                          // use transient so we doesnt exist on database
    public boolean hasvalueforgetOpeningamt = false;

    private double outstandingamt;

    @Transient
    public boolean hasvalueforgetOutstandingamt = false;

    private double paymentamt;

    @Transient
    public boolean hasvalueforgetPaymentamt = false;

    private String phone;

    private double recieveamt;

    @Transient
    public boolean hasvalueforgetRecieveamt = false;
    private String workingarea;

    //fields with relationships (one to many and many to many)

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "customers", allowSetters = true)
    private List<Order> orders = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "agentcode", nullable = false)
    @JsonIgnoreProperties(value = "customers", allowSetters = true)
    private Agent agent;

    //constructors


    public Customer()
    {
        //required by JPA
    }

    public Customer(        // order matters, make it the sam eas the seed data
        String phone,
        String custcity,
        String custcountry,
        String custname,
        String grade,
        double openingamt,
        double outstandingamt,
        double paymentamt,
        double recieveamt,
        String workingarea,
        Agent agent)
    {
        this.custcity = custcity;
        this.custcountry = custcountry;
        this.custname = custname;
        this.grade = grade;
        this.openingamt = openingamt;
        this.outstandingamt = outstandingamt;
        this.paymentamt = paymentamt;
        this.phone = phone;
        this.recieveamt = recieveamt;
        this.workingarea = workingarea;
        this.agent = agent;
    }

    public long getCustcode()
    {
        return custcode;
    }

    public void setCustcode(long custcode)
    {
        this.custcode = custcode;
    }

    public String getCustcity()
    {
        return custcity;
    }

    public void setCustcity(String custcity)
    {
        this.custcity = custcity;
    }

    public String getCustcountry()
    {
        return custcountry;
    }

    public void setCustcountry(String custcountry)
    {
        this.custcountry = custcountry;
    }

    public String getCustname()
    {
        return custname;
    }

    public void setCustname(String custname)
    {
        this.custname = custname;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public double getOpeningamt()
    {
        return openingamt;
    }

    public void setOpeningamt(double openingamt)
    {
        hasvalueforgetOpeningamt = true;
        this.openingamt = openingamt;
    }

    public double getOutstandingamt()
    {
        return outstandingamt;
    }

    public void setOutstandingamt(double outstandingamt)
    {
        hasvalueforgetOutstandingamt = true;
        this.outstandingamt = outstandingamt;
    }

    public double getPaymentamt()
    {
        return paymentamt;
    }

    public void setPaymentamt(double paymentamt)
    {
        hasvalueforgetPaymentamt = true;
        this.paymentamt = paymentamt;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public double getRecieveamt()
    {
        return recieveamt;
    }

    public void setRecieveamt(double recieveamt)
    {
        hasvalueforgetRecieveamt = true;
        this.recieveamt = recieveamt;
    }

    public String getWorkingarea()
    {
        return workingarea;
    }

    public void setWorkingarea(String workingarea)
    {
        this.workingarea = workingarea;
    }

    public List<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }

    public Agent getAgent()
    {
        return agent;
    }

    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }
}
