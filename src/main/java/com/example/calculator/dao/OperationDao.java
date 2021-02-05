package com.example.calculator.dao;

import com.example.calculator.domain.Operation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OperationDao {
    void createOperation(Operation operation);

    List<Operation> readFilteredOperations(String expression, Date startDate, Date endDate, String username);

    Operation readOperation(UUID id);
}
