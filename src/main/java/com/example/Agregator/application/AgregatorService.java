package com.example.Agregator.application;

import com.example.Agregator.threads.ConsumerThreads;
import com.example.Agregator.threads.ProducerThreads;

public class AgregatorService {
    public static void InitializeConsumerThreads() {
        try {
            for (int i = 0; i < 6; i++) {
                new ConsumerThreads("Consumer thread " + String.valueOf(i));
                //Thread.sleep(500);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void InitializeProducerThreads() {
        try {
            for (int i = 0; i < 6; i++) {
                new ProducerThreads("Producer thread " + String.valueOf(i));
                //Thread.sleep(500);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
