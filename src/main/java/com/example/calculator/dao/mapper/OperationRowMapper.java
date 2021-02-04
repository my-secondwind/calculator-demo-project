package com.example.calculator.dao.mapper;

import com.example.calculator.domain.Operation;
import com.example.calculator.domain.builder.OperationBuilder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class OperationRowMapper implements RowMapper<Operation> {
    @Override
    public Operation mapRow(ResultSet resultSet, int i) throws SQLException {
        return new OperationBuilder()
                .withId(UUID.fromString(resultSet.getString("id")))
                .withExpression(resultSet.getString("expression"))
                .withResult(resultSet.getString("result"))
                .withEnterDate(resultSet.getDate("enterDate"))
                .withUser(resultSet.getLong("userId"))
                .build();
    }
}
