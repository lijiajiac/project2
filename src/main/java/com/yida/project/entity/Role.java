package com.yida.project.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;

/**
 * (Role)实体类
 *
 * @author makejava
 * @since 2022-03-10 19:04:53
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 612507896974743561L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")//获取主键自增长id的值，
    private Integer id;

    private String name;

    private HashSet<Permission> permission;

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

    public HashSet<Permission> getPermission() {
        return permission;
    }

    public void setPermission(HashSet<Permission> permission) {
        this.permission = permission;
    }
}

