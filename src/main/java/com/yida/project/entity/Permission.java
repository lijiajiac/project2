package com.yida.project.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * (Permission)实体类
 *
 * @author makejava
 * @since 2022-03-10 19:05:13
 */
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = -16078036184962237L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")//获取主键自增长id的值，
    private Integer id;

    private String name;

    private String url;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

