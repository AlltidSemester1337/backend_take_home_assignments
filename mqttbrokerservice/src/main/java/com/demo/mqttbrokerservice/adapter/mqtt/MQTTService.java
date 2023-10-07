package com.demo.mqttbrokerservice.adapter.mqtt;

import com.demo.mqttbrokerservice.core.model.MQTTBrokerInformation;

public interface MQTTService {
    void broadCastMessage(MQTTBrokerInformation MQTTBrokerInformation, String topicName, String message);

    String awaitNextMessage(MQTTBrokerInformation MQTTBrokerInformation, String topicName) throws InterruptedException;
}
