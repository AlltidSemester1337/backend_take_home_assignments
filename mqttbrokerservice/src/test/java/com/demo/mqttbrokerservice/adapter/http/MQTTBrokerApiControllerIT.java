package com.demo.mqttbrokerservice.adapter.http;

import com.demo.mqttbrokerservice.TestConstants;
import com.demo.mqttbrokerservice.adapter.mqtt.MQTTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class MQTTBrokerApiControllerIT {


    private MockMvc mockMvc;

    @MockBean
    private MQTTService mockMqttService;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void whenRegister_withValidData_shouldRespondWithCreated() throws Exception {
        ResultActions registerResult = registerBroker();
        registerResult.andExpect(status().isCreated());
    }

    private ResultActions registerBroker() throws Exception {
        final String requestBody = String.format("""
                {
                  "hostName": "%s",
                  "port": %d
                }
                """, TestConstants.BROKER_INFORMATION.hostName(), TestConstants.BROKER_INFORMATION.port());

        return mockMvc.perform(put("/mqtt/" + TestConstants.BROKER_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody));
    }

    @Test
    void whenGet_withValidData_shouldRespondWithExpectedOkResponse() throws Exception {
        registerBroker();
        mockMvc.perform(get("/mqtt/" + TestConstants.BROKER_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                {
                          "hostName": "hostName",
                          "port": -1
                        }"""));
    }

    @Test
    void whenDelete_withValidBrokername_shouldRespondWithOkResponse() throws Exception {
        registerBroker();
        mockMvc.perform(delete("/mqtt/" + TestConstants.BROKER_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenSend_shouldBroadcastMessage() throws Exception {
        doNothing().when(mockMqttService).broadCastMessage(any(), anyString(), anyString());

        sendTestMessage()
                .andExpect(status().isOk());

        verify(mockMqttService).broadCastMessage(any(), eq(TestConstants.TEST_TOPIC), eq(TestConstants.TEST_MESSAGE));
    }

    private ResultActions sendTestMessage() throws Exception {
        return mockMvc.perform(post("/mqtt/" + TestConstants.BROKER_NAME + "/send/" + TestConstants.TEST_TOPIC)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestConstants.TEST_MESSAGE));
    }

    @Test
    void whenReceive_givenIncomingMessage_shouldRespondWithExpectedMessage() throws Exception {
        when(mockMqttService.awaitNextMessage(any(), eq(TestConstants.TEST_TOPIC))).thenReturn(TestConstants.TEST_MESSAGE);

        mockMvc.perform(get("/mqtt/" + TestConstants.BROKER_NAME + "/receive/" + TestConstants.TEST_TOPIC)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(TestConstants.TEST_MESSAGE));
    }


}