package com.example.calculator.service;

import com.example.calculator.domain.Operation;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OperationService {

    Operation create(Operation operation, User user);

    List<Operation> readFiltered(String expression, Date startDate, Date endDate, String username);

    Operation read(UUID id);
}
