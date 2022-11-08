package com.example.server3;

import com.example.server3.resources.KitchenServiceFirstCooks;
import com.example.server3.resources.KitchenServiceSecondCooks;

public class Server3Service {
    public static void InitializeFirstCooks() {
        try {
            for (int i = 0; i < 6; i++) {
                new KitchenServiceFirstCooks("Cook1 " + String.valueOf(i));
                //Thread.sleep(500);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void InitializeSecondCooks() {
        try {
            for (int i = 0; i < 6; i++) {
                new KitchenServiceSecondCooks("Cook2 " + String.valueOf(i));
                //Thread.sleep(500);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
