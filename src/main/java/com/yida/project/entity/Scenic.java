package com.yida.project.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 景区 javabean类
 */
@Table(name = "s_scenic")
public class Scenic implements Serializable {
    private Integer id;
    private String name;

    public Scenic() {
    }

    public Scenic(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Scenic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
