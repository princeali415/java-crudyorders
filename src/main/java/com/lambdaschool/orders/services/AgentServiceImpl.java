package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "agentServices")
public class AgentServiceImpl implements AgentServices
{
    @Autowired
    private AgentsRepository agentsRepository;

    @Transactional
    @Override
    public Agent save(Agent agent)
    {
        return agentsRepository.save(agent);
    }

    @Override
    public Agent findAgentById(long agentcode)
    {
        Agent agent = agentsRepository.findById(agentcode)
        .orElseThrow(()-> new EntityNotFoundException("Agent " + agentcode + " Not Found")); //handles if doesnt exist
        return agent;
    }
}

