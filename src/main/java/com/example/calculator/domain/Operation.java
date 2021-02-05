package com.example.calculator.domain;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Operation {
    private final UUID id;
    private final String expression;
    private String result;
    private final Date enterDate;
    private final String username;

    public Operation(UUID id, String expression, String result, Date enterDate, String username) {
        this.id = id;
        this.expression = expression;
        this.result = result;
        this.enterDate = enterDate;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public String getExpression() {
        return expression;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public String getUsername() {
        return username;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", expression='" + expression + '\'' +
                ", result='" + result + '\'' +
                ", enterDate=" + enterDate +
                ", user='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id) &&
                Objects.equals(expression, operation.expression) &&
                Objects.equals(result, operation.result) &&
                Objects.equals(enterDate, operation.enterDate) &&
                Objects.equals(username, operation.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expression, result, enterDate, username);
    }
}
