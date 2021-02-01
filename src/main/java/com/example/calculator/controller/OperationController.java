package com.example.calculator.controller;

import com.example.calculator.domain.Operation;
import com.example.calculator.service.OperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class OperationController {
    private final OperationService operationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationController.class);

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping(value = "/contracts")
    public ResponseEntity<?> create(@RequestBody Operation contract) {
        operationService.create(contract);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/contracts")
    public ResponseEntity<List<Operation>> read() {
        final List<Operation> contracts = operationService.readAll();

        return contracts != null && !contracts.isEmpty()
                ? new ResponseEntity<>(contracts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/contracts/{id}")
    public ResponseEntity<Operation> read(@PathVariable(name = "id") int id) {
        final Operation contract = operationService.read(id);

        return contract != null
                ? new ResponseEntity<>(contract, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
