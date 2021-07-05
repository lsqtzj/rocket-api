package com.github.alenfive.rocketapi.config;

import com.github.alenfive.rocketapi.datasource.DataSourceDialect;
import com.github.alenfive.rocketapi.datasource.DataSourceManager;
import com.github.alenfive.rocketapi.datasource.MongoDataSource;
import com.github.alenfive.rocketapi.datasource.MySQLDataSource;
import com.github.alenfive.rocketapi.datasource.SQLServerDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 默认数据源管理器
 */
@Component
public class DefaultDataSourceManager extends DataSourceManager {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        Map<String, DataSourceDialect> dialects = new LinkedHashMap<>();
        dialects.put("sqlserver", new SQLServerDataSource(jdbcTemplate, true));
        //dialects.put("mysql",new MySQLDataSource(jdbcTemplate,false));
        //dialects.put("postgres",new PostgreSQLDataSource(jdbcTemplate,false));
        //dialects.put("oracle",new OracleDataSource(jdbcTemplate,true));
        //dialects.put("mongodb",new MongoDataSource(mongoTemplate,true));
        super.setDialectMap(dialects);
    }
}
