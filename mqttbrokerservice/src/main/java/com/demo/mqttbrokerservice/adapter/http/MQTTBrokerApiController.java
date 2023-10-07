package com.demo.mqttbrokerservice.adapter.http;

import com.demo.mqttbrokerservice.adapter.http.dto.MQTTBrokerInformationDTO;
import com.demo.mqttbrokerservice.adapter.mqtt.MQTTService;
import com.demo.mqttbrokerservice.core.model.MQTTBrokerInformation;
import com.demo.mqttbrokerservice.core.service.MQTTBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = "/mqtt",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MQTTBrokerApiController {

    private final MQTTBrokerService MQTTBrokerService;
    @Autowired
    private final MQTTService mqttService;


    public MQTTBrokerApiController(MQTTBrokerService MQTTBrokerService, MQTTService mqttService) {
        this.MQTTBrokerService = MQTTBrokerService;
        this.mqttService = mqttService;
    }

    @PutMapping(path = "/{brokerName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerBroker(
            @PathVariable("brokerName") final String brokerName,
            @RequestBody final MQTTBrokerInformationDTO MQTTBrokerInformationDTO) {
        MQTTBrokerService.registerBroker(brokerName, MQTTBrokerInformation.fromDto(MQTTBrokerInformationDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/{brokerName}")
    public ResponseEntity<MQTTBrokerInformationDTO> getBrokerInformation(
            @PathVariable("brokerName") final String brokerName) {
        MQTTBrokerInformationDTO MQTTBrokerInformationDTO = MQTTBrokerInformation.toDto(MQTTBrokerService.getBrokerInformation(brokerName));
        return ResponseEntity.status(HttpStatus.OK).body(MQTTBrokerInformationDTO);
    }

    @DeleteMapping(path = "/{brokerName}")
    public ResponseEntity<Void> deleteBrokerInformation(
            @PathVariable("brokerName") final String brokerName) {
        MQTTBrokerService.deleteBrokerInformation(brokerName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = "/{brokerName}/send/{topicName}")
    public ResponseEntity<Void> sendBrokerMessage(
            @PathVariable("brokerName") final String brokerName,
            @PathVariable("topicName") final String topicName,
            @RequestBody final String message) {
        MQTTBrokerInformation MQTTBrokerInformation = MQTTBrokerService.getBrokerInformation(brokerName);
        mqttService.broadCastMessage(MQTTBrokerInformation, topicName, message);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "/{brokerName}/receive/{topicName}")
    public ResponseEntity<String> getNextMessage(
            @PathVariable("brokerName") final String brokerName,
            @PathVariable("topicName") final String topicName) {
        MQTTBrokerInformation MQTTBrokerInformation = MQTTBrokerService.getBrokerInformation(brokerName);

        try {
            String message = mqttService.awaitNextMessage(MQTTBrokerInformation, topicName);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
        }
    }
}
