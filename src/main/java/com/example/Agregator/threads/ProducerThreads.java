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

public class ProducerThreads implements Runnable {

    Thread t;
    String threadName;
    int id;
    public static final ReentrantLock mutex = new ReentrantLock();
    public static BlockingQueue<Object> objects = new LinkedBlockingQueue<>();
    static HttpHeaders headers = new HttpHeaders();
    private static RestTemplate restTemplate = new RestTemplate();

    public ProducerThreads(String thread) {
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
                    var object = objects.take();
                  //  System.out.println("ThreadId" + Thread.currentThread().getId() + "is processing the order with id " + order.getId());
                    sendObject(object);
                  // System.out.println("ThreadId " + Thread.currentThread().getId() + "sent the order with id " + object.getId() + "to producer");
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

    public static void sendObject(Object object) {
        // set the media type of http header request
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create an entity which encapsulated the body (order) and the header of http request
        HttpEntity<Object> entity = new HttpEntity<>(object, headers);
        // send current order to kitchen
         restTemplate.postForEntity("http://localhost:8080/producer/object", entity, Object.class);
    }

    public static void addObject(Object object) {
        objects.add(object);
    }
}
