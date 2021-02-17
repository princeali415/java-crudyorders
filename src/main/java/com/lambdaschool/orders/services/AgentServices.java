package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;

public interface AgentServices
{
    public Agent save(Agent agent);

    Agent findAgentById(long agentcode); // get agent by id
}
