package com.yanlei.springboot.service.CustomerService;

import java.util.Map;

/**
 * @Author: x
 * @Date: Created in 17:09 2018/11/22
 */
public interface CustomerService {
    Map<String, Object> getSchemeShowCustomer(String start);

    String updatePersonStart(Integer id);

    String getSchemeLog(Integer id);

    String getMsg(String telephone);

    String overScheme(Integer id);
}
