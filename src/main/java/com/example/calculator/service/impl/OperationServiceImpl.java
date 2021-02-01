package com.example.calculator.service.impl;

import com.example.calculator.dao.OperationDao;
import com.example.calculator.domain.Operation;
import com.example.calculator.service.OperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationServiceImpl implements OperationService {
    private final OperationDao operationDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);

    public OperationServiceImpl(OperationDao operationDao) {
        this.operationDao = operationDao;
    }

    @Override
    public void create(Operation operation) {
        LOGGER.info("add operation{}", operationDao);
        operationDao.createOperation(operation);
    }

    @Override
    public List<Operation> readAll() {
        LOGGER.info("read all operations");
        return operationDao.readAllOperations();
    }

    @Override
    public Operation read(long id) {
        LOGGER.info("read operation with {}", id);
        return operationDao.readOperation(id);
    }
}
