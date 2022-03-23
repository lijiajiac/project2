package com.yida.project.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 交通信息javabean类
 */
@Table(name = "s_serverthree")
public class Serverthree implements Serializable {
    @Id
    private Integer id;
    private String title;
    private String longitude;
    private String latitude;

    public Serverthree() {
    }

    public Serverthree(String title, String longitude, String latitude) {
        this.title = title;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Serverthree{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
