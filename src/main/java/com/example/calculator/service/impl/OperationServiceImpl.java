package com.example.calculator.service.impl;

import com.example.calculator.dao.OperationDao;
import com.example.calculator.domain.Operation;
import com.example.calculator.domain.builder.OperationBuilder;
import com.example.calculator.service.Calculator;
import com.example.calculator.service.OperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {
    private final OperationDao operationDao;
    private final Calculator calculator;
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);

    public OperationServiceImpl(OperationDao operationDao, Calculator calculator) {
        this.operationDao = operationDao;
        this.calculator = calculator;
    }

    @Override
    public Operation create(Operation operation) {
        String operationResult = calculator.calculate(operation.getExpression());

        Operation operationToSave = new OperationBuilder()
                .withId(UUID.randomUUID())
                .withExpression(operation.getExpression())
                .withResult(operationResult)
                .withEnterDate(new Date())
                .withUser(1L)
                .build();
        LOGGER.info("add operation{}", operationToSave);
        operationDao.createOperation(operationToSave);
        return operationToSave;
    }

    @Override
    public List<Operation> readAll() {
        LOGGER.info("read all operations");
        return operationDao.readAllOperations();
    }

    @Override
    public Operation read(UUID id) {
        LOGGER.info("read operation with {}", id);
        return operationDao.readOperation(id);
    }
}
