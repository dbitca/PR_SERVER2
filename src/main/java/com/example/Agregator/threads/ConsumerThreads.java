package com.example.Agregator.threads;

import com.example.Agregator.models.Object;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerThreads implements Runnable {

    Thread t;
    String threadName;
    int id;
    public static final ReentrantLock mutex = new ReentrantLock();
    public static BlockingQueue<Object> objects = new LinkedBlockingQueue<>();
    static HttpHeaders headers = new HttpHeaders();

    private static RestTemplate restTemplate = new RestTemplate();

    public ConsumerThreads(String thread) {
        threadName = thread;
        t = new Thread(this, threadName);
        System.out.println("New thread :" + t);
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            mutex.lock();
            try {
                if (objects.size() > 0) {
                    var order = objects.take();
                    sendOrders(order);
                    // System.out.println("ThreadId " + Thread.currentThread().getId() + "sent the order with id " + order.getId() + "to consumer");
                    Thread.sleep(500);
                   // System.out.println("Capacity of queue " + objects.size());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException();
            } finally {
                mutex.unlock();
            }
        }
    }

    public static void sendOrders(Object object) {
        // set the media type of http header request
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create an entity which encapsulated the body (order) and the header of http request
        HttpEntity<Object> entity = new HttpEntity<>(object, headers);
        // send current order to consumer
         restTemplate.postForEntity("http://localhost:8081/consumer/object", entity, Object.class);

    }

    public static void addOrder(Object object) {
        // add incoming order to queue
        objects.add(object);
    }
}
