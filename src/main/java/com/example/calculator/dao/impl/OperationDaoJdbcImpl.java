package com.example.calculator.dao.impl;

import com.example.calculator.dao.OperationDao;
import com.example.calculator.dao.mapper.OperationRowMapper;
import com.example.calculator.domain.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class OperationDaoJdbcImpl implements OperationDao {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO operation (id, expression, result, enterDate, userId) " +
            "VALUES (:id, :expression, :result, :enterDate, :userId) ";

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
    public List<Operation> readFilteredOperations(String expression, Date startDate, Date endDate, String username) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String request = "WHERE enterDate >= '" + dateFormat.format(startDate) +
                         "' AND enterDate <= '" + dateFormat.format(endDate) + "'";
        if (expression!= null){
            request = request + " AND expression = '" + expression + "'";
        }
        if (username!= null){
            request = request + " AND username = '" + username + "'";
        }
        return jdbcTemplate.query("SELECT * FROM operation " + request, new OperationRowMapper());
    }

    @Override
    public Operation readOperation(UUID id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from operation where id = ?", new OperationRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
