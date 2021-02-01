package com.example.calculator.dao;

import com.example.calculator.domain.Operation;

import java.util.List;

public interface OperationDao {
    void createOperation(Operation operation);

    List<Operation> readAllOperations();

    Operation readOperation(long id);
}
