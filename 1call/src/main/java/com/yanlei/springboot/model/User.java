package com.yanlei.springboot.model;

/**
 * @Author: x
 * @Date: Created in 15:39 2018/12/3
 */
public class User {

    private String id;
    private String name;
    private String phone;
    private String uLoginName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getuLoginName() {
        return uLoginName;
    }

    public void setuLoginName(String uLoginName) {
        this.uLoginName = uLoginName;
    }
}
