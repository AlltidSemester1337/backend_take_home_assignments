package com.demo.mqttbrokerservice.core.model;

import com.demo.mqttbrokerservice.adapter.http.dto.MQTTBrokerInformationDTO;

public record MQTTBrokerInformation(String hostName, int port) {

    public static MQTTBrokerInformation fromDto(final MQTTBrokerInformationDTO MQTTBrokerInformationDTO) {
        return new MQTTBrokerInformation(MQTTBrokerInformationDTO.hostName(), MQTTBrokerInformationDTO.port());
    }

    public static MQTTBrokerInformationDTO toDto(MQTTBrokerInformation MQTTBrokerInformation) {
        return new MQTTBrokerInformationDTO(MQTTBrokerInformation.hostName(), MQTTBrokerInformation.port());
    }
}
