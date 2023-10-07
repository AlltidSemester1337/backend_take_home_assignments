package com.demo.mqttbrokerservice.adapter.mqtt;

import com.demo.mqttbrokerservice.core.model.MQTTBrokerInformation;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MQTTServiceImpl implements MQTTService {

    private final String username;
    private final String password;

    public MQTTServiceImpl(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void broadCastMessage(MQTTBrokerInformation MQTTBrokerInformation, String topicName, String message) {
        // TODO: 2023-09-28 Scoped out doing this properly (probably using testConfiguration?)
        final Mqtt5BlockingClient client = username.isBlank() && password.isBlank() ? createClientNoAuth(MQTTBrokerInformation) : createClientWithAuth(MQTTBrokerInformation, username, password);

        client.publishWith()
                .topic(topicName)
                .payload(UTF_8.encode(message))
                .send();

        client.disconnect();
    }

    @Override
    public String awaitNextMessage(MQTTBrokerInformation MQTTBrokerInformation, String topicName) throws InterruptedException {
        final Mqtt5BlockingClient client = username.isBlank() && password.isBlank() ? createClientNoAuth(MQTTBrokerInformation) : createClientWithAuth(MQTTBrokerInformation, username, password);

        client.subscribeWith()
                .topicFilter(topicName)
                .qos(MqttQos.EXACTLY_ONCE)
                .send();

        Mqtt5BlockingClient.Mqtt5Publishes publishedMessages = client.publishes(MqttGlobalPublishFilter.SUBSCRIBED);
        Mqtt5Publish receivedMessage = publishedMessages.receive();
        final String message = new String(receivedMessage.getPayloadAsBytes(), StandardCharsets.UTF_8);

        client.disconnect();

        return message;
    }

    private Mqtt5BlockingClient createClientNoAuth(MQTTBrokerInformation MQTTBrokerInformation) {
        final Mqtt5BlockingClient client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(MQTTBrokerInformation.hostName())
                .serverPort(MQTTBrokerInformation.port())
                .buildBlocking();

        client.connect();

        return client;
    }

    private Mqtt5BlockingClient createClientWithAuth(MQTTBrokerInformation MQTTBrokerInformation, String username, String password) {
        final Mqtt5BlockingClient client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(MQTTBrokerInformation.hostName())
                .serverPort(MQTTBrokerInformation.port())
                .sslWithDefaultConfig()
                .buildBlocking();

        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send();

        return client;
    }
}
