package com.demo.mqttbrokerservice.adapter.mqtt;

import com.demo.mqttbrokerservice.core.model.MQTTBrokerInformation;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.hivemq.HiveMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
class MQTTServiceImplIT {

    @Container
    HiveMQContainer container = new HiveMQContainer(DockerImageName.parse("hivemq/hivemq-ce:latest"));

    private Mqtt5BlockingClient testClient;
    private ScheduledExecutorService scheduledExecutorService;

    @BeforeEach
    void setUp() {
        testClient = Mqtt5Client.builder()
                .serverPort(container.getMqttPort())
                .buildBlocking();
        testClient.connect();
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @AfterEach
    void tearDown() {
        testClient.disconnect();
    }

    @Test
    void givenValidBrokerInfoTopicAndMessage_shouldBroadcast() throws InterruptedException {
        MQTTServiceImpl mqttService = new MQTTServiceImpl("", "");
        String testMsg = "testMsg";
        String testTopic = "testTopic";

        testClient.subscribeWith()
                .topicFilter(testTopic)
                .qos(MqttQos.EXACTLY_ONCE)
                .send();

        Mqtt5BlockingClient.Mqtt5Publishes publishedMessages = testClient.publishes(MqttGlobalPublishFilter.SUBSCRIBED);

        mqttService.broadCastMessage(new MQTTBrokerInformation(container.getHost(), container.getMqttPort()), testTopic, testMsg);

        Mqtt5Publish receivedMessage = publishedMessages.receive();
        final String payload = new String(receivedMessage.getPayloadAsBytes(), UTF_8);
        assertEquals(testMsg, payload);
    }

    @Test
    void givenValidBrokerInfoTopicAndMessageSent_shouldReturnExpectedMessage() throws InterruptedException {
        MQTTServiceImpl mqttService = new MQTTServiceImpl("", "");
        String testMsg = "testMsg";
        String testTopic = "testTopic";

        scheduledExecutorService.schedule(() -> testClient.publishWith()
                .topic(testTopic)
                .payload(UTF_8.encode(testMsg))
                .send(), 200, TimeUnit.MILLISECONDS);

        String receivedMessage = mqttService.awaitNextMessage(new MQTTBrokerInformation(container.getHost(), container.getMqttPort()), testTopic);

        assertEquals(testMsg, receivedMessage);
    }
}