package com.example.counter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CounterService {

    private final ConcurrentHashMap<String, AtomicInteger> counters;

    @Autowired
    public CounterService() {
        counters = new ConcurrentHashMap<>();
    }

    public Optional<Integer> create(String name) {
        var oldValue = counters.putIfAbsent(name, new AtomicInteger(1));
        return oldValue == null ? Optional.of(1) : Optional.empty();
    }

    public Optional<Integer> increment(String name) {
        AtomicInteger localCounterValue = new AtomicInteger();
        var computeResult = counters.computeIfPresent(name, (key, val) -> {
            localCounterValue.set(val.incrementAndGet());
            return val;
        });
        return computeResult != null ? Optional.of(localCounterValue.intValue()) : Optional.empty();
    }

    public Optional<Integer> get(String name) {
        var value = counters.get(name);
        return value == null ? Optional.empty() : Optional.of(value.intValue());
    }

    public boolean delete(String name) {
        return counters.remove(name) != null;
    }

    public int sum() {
        return counters.reduceEntriesToInt(10000, entry -> entry.getValue().intValue(), 0, Integer::sum);
    }

    public Set<String> names() {
        return counters.keySet();
    }
}
