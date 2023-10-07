package com.demo.mqttbrokerservice.core.service;

import com.demo.mqttbrokerservice.core.model.MQTTBrokerInformation;

public interface MQTTBrokerService {

    void registerBroker(String brokerName, MQTTBrokerInformation MQTTBrokerInformation);

    MQTTBrokerInformation getBrokerInformation(String brokerName);

    void deleteBrokerInformation(String brokerName);
}
