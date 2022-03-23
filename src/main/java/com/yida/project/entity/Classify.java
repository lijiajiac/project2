package com.yida.project.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 周边列表下拉javabean类
 */
@Table(name = "s_classify")
public class Classify implements Serializable {
    @Id
    private Integer id;
    private String name;

    public Classify() {
    }

    public Classify(String name) {
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
        return "Classify{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
