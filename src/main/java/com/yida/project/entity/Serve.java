package com.yida.project.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 景区咨询对应javabean类
 */
@Table(name = "s_serve")
public class Serve implements Serializable {
    @Id
    private Integer id;
    private String title;
    private String tho;

    public Serve() {
    }

    public Serve(String title, String tho) {
        this.title = title;
        this.tho = tho;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTho() {
        return tho;
    }

    public void setTho(String tho) {
        this.tho = tho;
    }

    @Override
    public String toString() {
        return "Serve{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", tho='" + tho + '\'' +
                '}';
    }
}
