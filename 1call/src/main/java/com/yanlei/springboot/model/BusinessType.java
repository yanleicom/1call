package com.yanlei.springboot.model;

/**
 * @Author: x
 * @Date: Created in 16:30 2018/11/7
 * 新建事项名称条用onecall数据库查询事项是否有符合名称
 *  有: 查询事项条件
 *  没有: 新建事项 新建事项条件
 */
public class BusinessType {

    private Integer id;
    private String name; //事项名
    private String acceptCondition; //事项条件

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcceptCondition() {
        return acceptCondition;
    }

    public void setAcceptCondition(String acceptCondition) {
        this.acceptCondition = acceptCondition;
    }
}
