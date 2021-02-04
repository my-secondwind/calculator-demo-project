package com.example.calculator.controller;

import com.example.calculator.domain.Operation;
import com.example.calculator.service.OperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class OperationController {
    private final OperationService operationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationController.class);

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping(value = "/operation")
    public ResponseEntity<?> create(@RequestBody Operation operation) {
        Operation savedOperation = operationService.create(operation);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOperation.getId())
                .toUri();
        return new ResponseEntity<>(Map.of(HttpHeaders.LOCATION, location), HttpStatus.CREATED);
    }

    @GetMapping(value = "/operation")
    public ResponseEntity<List<Operation>> read() {
        final List<Operation> operations = operationService.readAll();

        return operations != null && !operations.isEmpty()
                ? new ResponseEntity<>(operations, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/operation/{id}")
    public ResponseEntity<Operation> read(@PathVariable(name = "id") UUID id) {
        final Operation contract = operationService.read(id);

        return contract != null
                ? new ResponseEntity<>(contract, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
