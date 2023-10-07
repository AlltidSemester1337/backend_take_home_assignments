package com.demo.mqttbrokerservice.core.service;

import com.demo.mqttbrokerservice.TestConstants;
import com.demo.mqttbrokerservice.core.model.MQTTBrokerInformation;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MQTTBrokerServiceImplTest {

    @Test
    void registerExpectedBrokerInformation() {
        Map<String, MQTTBrokerInformation> testMap = new HashMap<>();
        MQTTBrokerServiceImpl brokerService = new MQTTBrokerServiceImpl(testMap);
        brokerService.registerBroker(TestConstants.BROKER_NAME, TestConstants.BROKER_INFORMATION);
        assertEquals(1, testMap.size());
        assertEquals(TestConstants.BROKER_INFORMATION, testMap.get(TestConstants.BROKER_NAME));
    }

    @Test
    void givenValidBrokerName_whenGetBrokerInformation_returnsExpectedBrokerInformation() {
        Map<String, MQTTBrokerInformation> testMap = new HashMap<>();
        testMap.put(TestConstants.BROKER_NAME, TestConstants.BROKER_INFORMATION);
        MQTTBrokerServiceImpl brokerService = new MQTTBrokerServiceImpl(testMap);
        assertEquals(TestConstants.BROKER_INFORMATION, brokerService.getBrokerInformation(TestConstants.BROKER_NAME));
    }

    @Test
    void givenValidBrokerName_whenDeleteBrokerInformation_returnsExpectedResult() {
        Map<String, MQTTBrokerInformation> testMap = new HashMap<>();
        String anotherBrokerName = TestConstants.BROKER_NAME + "2";

        testMap.put(TestConstants.BROKER_NAME, TestConstants.BROKER_INFORMATION);
        testMap.put(anotherBrokerName, TestConstants.BROKER_INFORMATION);
        MQTTBrokerServiceImpl brokerService = new MQTTBrokerServiceImpl(testMap);

        assertEquals(2, testMap.size());
        brokerService.deleteBrokerInformation(TestConstants.BROKER_NAME);

        assertEquals(1, testMap.size());
        assertEquals(TestConstants.BROKER_INFORMATION, brokerService.getBrokerInformation(anotherBrokerName));
    }

}