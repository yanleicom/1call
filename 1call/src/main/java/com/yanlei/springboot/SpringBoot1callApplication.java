package com.yanlei.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@PropertySource("classpath:application.properties")
@MapperScan("com.yanlei.springboot.mapper")
@EnableScheduling // 开启定时任务 必须!
public class SpringBoot1callApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot1callApplication.class, args);
	}
}
