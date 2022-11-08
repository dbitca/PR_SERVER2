package com.example.server3.resources;

import com.example.server3.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server3")
public class Server3Controller {
    Logger logger = LoggerFactory.getLogger(Server3Controller.class);

    @PostMapping("/orderHall")
    public ResponseEntity postOrderFromHall(@RequestBody Order order){
        logger.info("Order with id: " + order.getId() + " was received by server3 from hall.");
        KitchenServiceFirstCooks.addOrder(order);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/orderKitchen")
    public ResponseEntity postOrderFromKitchen(@RequestBody Order order){
        logger.info("Order with id: " + order.getId() + " was received by server3 from kitchen.");
        KitchenServiceSecondCooks.addOrder(order);
        return new ResponseEntity(HttpStatus.OK);
    }
}
