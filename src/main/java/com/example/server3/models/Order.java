package com.example.server3.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Order {
    private int id;

    public Order() {
    }

    @JsonCreator
    public Order(@JsonProperty("id") int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
