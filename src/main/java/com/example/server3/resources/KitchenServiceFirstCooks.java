package com.example.server3.resources;

import com.example.server3.models.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class KitchenServiceFirstCooks implements Runnable {

    Thread t;
    String cookName;
    int id;
    public static final ReentrantLock mutex = new ReentrantLock();
    public static BlockingQueue<Order> orders = new LinkedBlockingQueue<>();
    static HttpHeaders headers = new HttpHeaders();

    private static RestTemplate restTemplate = new RestTemplate();

    public KitchenServiceFirstCooks(String thread) {
        cookName = thread;
        t = new Thread(this, cookName);
        System.out.println("New thread :" + t);
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            mutex.lock();
            try {
                if (orders.size() > 0) {
                    var order = orders.take();
                  //  System.out.println("ThreadId" + Thread.currentThread().getId() + "is processing the order with id " + order.getId());
                    sendOrders(order);
                  //  System.out.println("ThreadId " + Thread.currentThread().getId() + "sent the order with id " + order.getId() + "to hall");
                    Thread.sleep(500);
                    //System.out.println("Capacity of queue " + orders.size());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException();
            } finally {
                mutex.unlock();
            }
        }
    }

    public static void sendOrders(Order order) {
        // set the media type of http header request
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create an entity which encapsulated the body (order) and the header of http request
        HttpEntity<Order> entity = new HttpEntity<>(order, headers);
        // send current order to kitchen
         restTemplate.postForEntity("http://localhost:8081/kitchen/order", entity, Order.class);
    }

    public static void addOrder(Order order) {
        // add incoming order to queue
        orders.add(order);
    }
}