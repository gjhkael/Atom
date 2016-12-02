package com.ctrip.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jtzhang
 */
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {
    /** 登陆用户名 */
    private String username;
    /** 登陆密码 */
    private String password;

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
