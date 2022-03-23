package com.yida.project.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2022-03-10 19:03:29
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -14969426825657193L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")//获取主键自增长id的值，
    private Integer id;

    private String name;

    private String password;

    private Integer state;

    private String salt;

    private HashSet<Role> roles;

    private HashSet<UserRole> userRoles;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public HashSet<Role> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<Role> roles) {
        this.roles = roles;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public HashSet<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(HashSet<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}

