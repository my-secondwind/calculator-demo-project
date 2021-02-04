package com.example.calculator.domain.builder;

import com.example.calculator.domain.Operation;

import java.util.Date;
import java.util.UUID;

public class OperationBuilder {
    private UUID id;
    private String expression;
    private String result;
    private Date enterDate;
    private Long userId;

    public OperationBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public OperationBuilder withExpression(String expression) {
        this.expression = expression;
        return this;
    }

    public OperationBuilder withResult(String result) {
        this.result = result;
        return this;
    }

    public OperationBuilder withEnterDate(Date enterDate) {
        this.enterDate = enterDate;
        return this;
    }

    public OperationBuilder withUser(Long userId) {
        this.userId = userId;
        return this;
    }

    public Operation build() {
        return new Operation(id, expression, result, enterDate, userId);
    }
}