package com.demo.mqttbrokerservice.config;

import com.demo.mqttbrokerservice.adapter.mqtt.MQTTService;
import com.demo.mqttbrokerservice.adapter.mqtt.MQTTServiceImpl;
import com.demo.mqttbrokerservice.core.service.MQTTBrokerService;
import com.demo.mqttbrokerservice.core.service.MQTTBrokerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQTTBrokerServiceConfiguration {

    @Bean
    MQTTBrokerService brokerService() {
        return new MQTTBrokerServiceImpl();
    }

    @Bean
    MQTTService mqttService() {
        //Scoped out loading from secrets file etc
        return new MQTTServiceImpl("hivemq.webclient.1695901621625", "aqQ&Pei!TC<g596Xn>7D");
    }

}
