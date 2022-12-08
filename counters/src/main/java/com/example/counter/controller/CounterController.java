package com.example.counter.controller;

import com.example.counter.dto.CounterName;
import com.example.counter.dto.responses.MessageResponse;
import com.example.counter.dto.responses.CounterValueResponse;
import com.example.counter.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/counter")
public class CounterController {

    private final CounterService counterService;

    @Autowired
    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCounter(@RequestBody CounterName counter) {
        var created = counterService.create(counter.name());
        return created.isPresent() ? new ResponseEntity<>(new CounterValueResponse(created.get()), HttpStatus.CREATED)
                : (new ResponseEntity<>(new MessageResponse("Name already exists"), HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/increment")
    public ResponseEntity<?> incrementCounter(@RequestBody CounterName counter) {
        var incremented = counterService.increment(counter.name());
        return incremented.isPresent() ? new ResponseEntity<>(new CounterValueResponse(incremented.get()), HttpStatus.OK)
                : (new ResponseEntity<>(new MessageResponse("Name doesn't exist"), HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/value")
    public ResponseEntity<?> getCounter(@RequestBody CounterName counter) {
        var optValue = counterService.get(counter.name());
        if(optValue.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("Name doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new CounterValueResponse(optValue.get()), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCounter(@RequestBody CounterName counter) {
        boolean deleted = counterService.delete(counter.name());
        return deleted ? new ResponseEntity<>(new MessageResponse("Counter was deleted"), HttpStatus.OK)
                : (new ResponseEntity<>(new MessageResponse("Name doesn't exist"), HttpStatus.BAD_REQUEST));
    }
}
