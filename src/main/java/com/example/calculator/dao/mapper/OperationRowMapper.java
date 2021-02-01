package com.example.calculator.dao.mapper;

import com.example.calculator.domain.Operation;
import com.example.calculator.domain.builder.OperationBuilder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationRowMapper implements RowMapper<Operation> {
    @Override
    public Operation mapRow(ResultSet resultSet, int i) throws SQLException {
        return new OperationBuilder()
                .withId(resultSet.getLong("id"))
                .withExpression(resultSet.getString("operation"))
                .withResult(resultSet.getString("result"))
                .withEnterDate(resultSet.getDate("enterDate"))
                .withUser(resultSet.getLong("userId"))
                .createOperation();
    }
}