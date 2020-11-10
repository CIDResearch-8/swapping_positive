package com.swappingpositive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

@Controller
@EnableAutoConfiguration

public class Controllers {

    //DB操作をするための処理
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Controllers(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
