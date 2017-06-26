package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author chenmo
 * @version [版本号, 2017/6/23]
 * @see [相关类/方法    ]
 * @since [产品/模块版本]
 */
@Table(name = "TEST")
public class TestEntity
{
    @Column(name = "ID")
    private Long id;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "SEX")
    private String sex;
    @Column(name = "MOBILE")
    private String mobile;
    @Column(name = "TYPE")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
