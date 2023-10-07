package com.demo.mqttbrokerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestMQTTBrokerserviceApplication {

	public static void main(String[] args) {
		SpringApplication.from(MQTTBrokerserviceApplication::main).with(TestMQTTBrokerserviceApplication.class).run(args);
	}

}
