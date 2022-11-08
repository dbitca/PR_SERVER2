package com.example.Agregator.application;

import com.example.Agregator.models.Object;
import com.example.Agregator.threads.ConsumerThreads;
import com.example.Agregator.threads.ProducerThreads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agregator")
public class AgregatorController {
    Logger logger = LoggerFactory.getLogger(AgregatorController.class);

    @PostMapping("/objectConsumer")
    public ResponseEntity postObjectConsumer (@RequestBody Object object){
        logger.info("Order with id: " + object.getId() + " was received by agregator from consumer.");
        ConsumerThreads.addOrder(object);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/objectProducer")
    public ResponseEntity postObjectProducer(@RequestBody Object object){
        logger.info("Order with id: " + object.getId() + " was received by agregator from producer.");
        ProducerThreads.addObject(object);
        return new ResponseEntity(HttpStatus.OK);
    }
}
