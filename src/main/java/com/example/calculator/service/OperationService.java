package com.example.calculator.service;

import com.example.calculator.domain.Operation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OperationService {

    Operation create(Operation operation);

    List<Operation> readFiltered(String expression, Date startDate, Date endDate);

    Operation read(UUID id);
}
