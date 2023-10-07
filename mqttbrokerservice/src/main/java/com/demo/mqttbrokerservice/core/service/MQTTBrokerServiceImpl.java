package com.demo.mqttbrokerservice.core.service;

import com.demo.mqttbrokerservice.core.model.MQTTBrokerInformation;

import java.util.HashMap;
import java.util.Map;

public class MQTTBrokerServiceImpl implements MQTTBrokerService {

    private final Map<String, MQTTBrokerInformation> brokers;

    public MQTTBrokerServiceImpl() {
        brokers = new HashMap<>();
    }

    MQTTBrokerServiceImpl(Map<String, MQTTBrokerInformation> brokers) {
        this.brokers = brokers;
    }

    @Override
    public void registerBroker(String brokerName, MQTTBrokerInformation MQTTBrokerInformation) {
        brokers.put(brokerName, MQTTBrokerInformation);
    }

    @Override
    public MQTTBrokerInformation getBrokerInformation(String brokerName) {
        return brokers.get(brokerName);
    }

    @Override
    public void deleteBrokerInformation(String brokerName) {
        brokers.remove(brokerName);
    }
}
