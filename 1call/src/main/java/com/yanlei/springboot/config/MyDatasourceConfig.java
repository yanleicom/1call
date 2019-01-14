package com.yanlei.springboot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 *  mydatabase 配置文件1号数据库
 */
@Configuration
@MapperScan(basePackages = "com.yanlei.springboot.mapper.myData", sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MyDatasourceConfig {

    /**
     * 配置数据数据源 master
     *
     * @return
     */
    @Bean(name = "masterDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.mydata")
    @Primary
    public DataSource masterDatasource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置session工厂
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
        return bean.getObject();
    }


    /**
     * 配置事务管理器
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "masterTransactionManger")
    @Primary
    public DataSourceTransactionManager masterTransactionManger(@Qualifier("masterDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);

    }

    /**
     * 模版
     *
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}