package com.yida.project.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 视频添加javabean类
 */
@Table(name = "s_video")
public class Video implements Serializable {
    @Id
    private Integer id;
    private String title;
    private Scenic scenic;
    private String thumbnail;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    private String spotImages;

    public Video() {
    }

    public Video(String title, Scenic scenic, String thumbnail, Date modifyTime, String spotImages) {
        this.title = title;
        this.scenic = scenic;
        this.thumbnail = thumbnail;
        this.modifyTime = modifyTime;
        this.spotImages = spotImages;
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

    public Scenic getScenic() {
        return scenic;
    }

    public void setScenic(Scenic scenic) {
        this.scenic = scenic;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSpotImages() {
        return spotImages;
    }

    public void setSpotImages(String spotImages) {
        this.spotImages = spotImages;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", scenic=" + scenic +
                ", thumbnail='" + thumbnail + '\'' +
                ", modifyTime=" + modifyTime +
                ", spotImages='" + spotImages + '\'' +
                '}';
    }
}
