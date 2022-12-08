package com.example.counter;

import com.example.counter.service.CounterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CounterServiceTest {

    @Autowired
    CounterService counterService;

    @AfterEach
    public void clear() {
        var names = counterService.names();
        for(var name : names) {
            counterService.delete(name);
        }
    }

    @Test
    public void createCounterTest() {
        var counter = "name1";
        var result = counterService.create(counter);
        assertThat(result.isPresent());
        assertThat(result.orElse(null))
                .isEqualTo(1);

        result = counterService.create(counter);
        assertThat(result.isEmpty());
    }

    @Test
    public void incrementCounterTest() {
        var counter = "name1";
        var result = counterService.create(counter);
        assertThat(result.isPresent());
        for(int i = 0; i < 10; ++i) {
            result = counterService.increment(counter);
        }
        assertThat(result.isPresent());
        assertThat(result.get()).isEqualTo(11);
    }

    @Test
    public void deleteCounterTest() {
        var counter = "name1";
        var result = counterService.create(counter);
        assertThat(result.isPresent());

        var deletionRes = counterService.delete(counter);
        assertThat(deletionRes);
        deletionRes = counterService.delete(counter);
        assertThat(!deletionRes);
    }

    @Test
    public void getCounterValueTest() {
        var counter = "name1";
        var result = counterService.get(counter);
        assertThat(result.isEmpty());

        result = counterService.create(counter);
        assertThat(result.isPresent());

        result = counterService.get(counter);
        assertThat(result.isPresent());
        assertThat(result.orElse(null)).isEqualTo(1);
    }

    @Test
    public void sumCountersTest() {
        Optional<Integer> result;
        for(int i = 0; i < 10; ++i) {
            result = counterService.create(Integer.toString(i));
            assertThat(result.isPresent());
        }
        var sum = counterService.sum();
        assertThat(sum).isEqualTo(10);
    }

    @Test
    public void getCountersNamesTest() {
        Optional<Integer> result;
        Set<String> names = new HashSet<>();
        for(int i = 0; i < 10; ++i) {
            var counter = Integer.toString(i);
            names.add(counter);
            result = counterService.create(counter);
            assertThat(result.isPresent());
        }
        var resNames = counterService.names();
        assertThat(names).allMatch(resNames::contains);
    }

}
