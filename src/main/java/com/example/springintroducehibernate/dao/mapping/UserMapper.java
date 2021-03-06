package com.example.springintroducehibernate.dao.mapping;

import com.example.springintroducehibernate.model.User;
import com.example.springintroducehibernate.model.impl.UserImpl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserImpl(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email")
        );
    }

}
