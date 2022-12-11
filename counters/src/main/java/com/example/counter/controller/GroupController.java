package com.example.counter.controller;

import com.example.counter.dto.responses.CounterValueResponse;
import com.example.counter.service.CounterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final CounterService counterService;

    public GroupController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("sum")
    public CounterValueResponse getSum() {
        var sum = counterService.sum();
        return new CounterValueResponse(sum);
    }

    @GetMapping("names")
    public Set<String> getNames() {
        return counterService.names();
    }
}
