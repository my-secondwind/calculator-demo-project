package com.example.calculator.dao.impl;

import com.example.calculator.dao.OperationDao;
import com.example.calculator.dao.mapper.OperationRowMapper;
import com.example.calculator.domain.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.List;

public class OperationDaoJdbcImpl implements OperationDao {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO operation (expression, result, enterDate, userId) " +
            "VALUES (:expression, :result, :enterDate, :userId) ";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void createOperation(Operation operation) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(operation);
        namedJdbcTemplate.update(INSERT_SQL, namedParameters);
    }

    @Override
    public List<Operation> readAllOperations() {
        return jdbcTemplate.query("SELECT * FROM insuranceContract", new OperationRowMapper());
    }

    @Override
    public Operation readOperation(long id) {
        return jdbcTemplate.queryForObject("SELECT * from insuranceContract where id = ?", new OperationRowMapper());
    }
}
