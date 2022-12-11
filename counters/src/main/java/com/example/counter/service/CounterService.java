package com.example.counter.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CounterService {

    private final ConcurrentHashMap<String, Integer> counters;

    public CounterService() {
        counters = new ConcurrentHashMap<>();
    }

    public int create(String name) throws IllegalArgumentException {
        var oldValue = counters.putIfAbsent(name, 1);
        if(oldValue != null) {
            throw new IllegalArgumentException("Counter already exists");
        }
        return 1;
    }

    public int increment(String name) throws IllegalArgumentException {
        var computeResult = counters.computeIfPresent(name, (key, val) -> val + 1);
        if(computeResult == null) {
            throw new IllegalArgumentException("Unknown counter");
        }
        return computeResult;
    }

    public int get(String name) throws IllegalArgumentException {
        var value = counters.get(name);
        if(value == null) {
            throw new IllegalArgumentException("Unknown counter");
        }
        return value;
    }

    public void delete(String name) throws IllegalArgumentException {
        if (counters.remove(name) == null) {
            throw new IllegalArgumentException("Unknown counter");
        }
    }

    public int sum() {
        return counters.reduceEntriesToInt(10000, Map.Entry::getValue, 0, Integer::sum);
    }

    public Set<String> names() {
        return counters.keySet();
    }
}
