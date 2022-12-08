package com.example.counter.controller;

import com.example.counter.dto.responses.CounterValueResponse;
import com.example.counter.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final CounterService counterService;

    @Autowired
    public GroupController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("sum")
    public ResponseEntity<?> getSum() {
        var sum = counterService.sum();
        return new ResponseEntity<>(new CounterValueResponse(sum), HttpStatus.OK);
    }

    @GetMapping("names")
    public ResponseEntity<?> getNames() {
        return new ResponseEntity<>(counterService.names(), HttpStatus.OK);
    }
}
