package com.example.calculator.service;

import com.example.calculator.domain.Operation;

import java.util.List;

public interface OperationService {

    void create(Operation operation);

    List<Operation> readAll();

    Operation read(long id);
}
