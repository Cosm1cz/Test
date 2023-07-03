package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class testcon {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> tet() {
        String s = "  ";
        String sql = "select * from test_data";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
}
