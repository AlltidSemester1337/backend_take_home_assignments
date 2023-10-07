package com.demo.mqttbrokerservice;

import com.demo.mqttbrokerservice.core.model.MQTTBrokerInformation;

public enum TestConstants {
    ;

    public static final String BROKER_NAME = "testBroker";
    public static final MQTTBrokerInformation BROKER_INFORMATION = new MQTTBrokerInformation("hostName", -1);
    public static final String TEST_TOPIC = "testTopic";
    public static final String TEST_MESSAGE = "testMessage";
}
