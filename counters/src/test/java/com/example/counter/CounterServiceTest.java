package com.example.counter;

import com.example.counter.service.CounterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CounterServiceTest {

    private final CounterService counterService = new CounterService();

    @AfterEach
    public void clear() {
        var names = counterService.names();
            for(var name : names) {
                counterService.delete(name);
        }
    }

    @Test
    public void createCounterNameDoesNotExistTest() {
        var counter = "name1";
        int result = counterService.create(counter);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void createCounterNameAlreadyExistsTest() {
        var counter = "name1";
        counterService.create(counter);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            counterService.create(counter);
        });

        String expectedMessage = "Counter already exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void incrementCounterTest() {
        var counter = "name1";
        int result = counterService.create(counter);
        for (int i = 0; i < 10; ++i) {
            result = counterService.increment(counter);
        }
        assertThat(result).isEqualTo(11);
    }

    @Test
    public void incrementUnknownCounterTest() {
        var counter = "name1";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            counterService.increment(counter);
        });

        String expectedMessage = "Unknown counter";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void deleteCounterTest() {
        var counter = "name1";
        counterService.create(counter);
        counterService.delete(counter);
    }

    @Test
    public void deleteUnknownCounterTest() {
        var counter = "name1";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            counterService.delete(counter);
        });

        String expectedMessage = "Unknown counter";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getCounterValueTest() {
        var counter = "name1";
        counterService.create(counter);
        var result = counterService.get(counter);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void getUnknownCounterValueTest() {
        var counter = "name1";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            counterService.get(counter);
        });

        String expectedMessage = "Unknown counter";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void sumCountersTest() {
        for(int i = 0; i < 10; ++i) {
            counterService.create(Integer.toString(i));
        }
        var sum = counterService.sum();
        assertThat(sum).isEqualTo(10);
    }

    @Test
    public void getCountersNamesTest() {
        Set<String> names = new HashSet<>();
        for (int i = 0; i < 10; ++i) {
            var counter = Integer.toString(i);
            names.add(counter);
            counterService.create(counter);
        }
        var resNames = counterService.names();
        assertThat(names).allMatch(resNames::contains);
    }
}
