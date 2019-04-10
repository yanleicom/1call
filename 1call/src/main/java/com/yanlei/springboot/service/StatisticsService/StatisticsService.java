package com.yanlei.springboot.service.StatisticsService;

/**
 * @Author: x
 * @Date: Created in 16:40 2019/1/8
 */
public interface StatisticsService {
    String getPersonCount();

    String getTrends();

    String getSchemePersonCount();

    String getSchemeStart();

    String getMatterPerson();

    int insertNotice(Integer id);
}
