package com.example.counter.controller;

import com.example.counter.dto.CounterName;
import com.example.counter.dto.responses.MessageResponse;
import com.example.counter.dto.responses.CounterValueResponse;
import com.example.counter.service.CounterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CounterController {

    private final CounterService counterService;

    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @PostMapping("/counter")
    public ResponseEntity<CounterValueResponse> createCounter(@RequestBody CounterName counter) throws Exception {
        int created = counterService.create(counter.name());
        return new ResponseEntity<>(new CounterValueResponse(created), HttpStatus.CREATED);
    }

    @PostMapping("/counter/increment")
    public CounterValueResponse incrementCounter(@RequestBody CounterName counter) throws Exception {
        int incremented = counterService.increment(counter.name());
        return new CounterValueResponse(incremented);
    }

    @PostMapping("/counter/value")
    public CounterValueResponse getCounter(@RequestBody CounterName counter) throws Exception {
        int value = counterService.get(counter.name());
        return new CounterValueResponse(value);
    }

    @DeleteMapping("/counter")
    public MessageResponse deleteCounter(@RequestBody CounterName counter) throws Exception{
        counterService.delete(counter.name());
        return new MessageResponse("Counter was deleted");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleException(Exception ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
