package com.yida.project.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * javabean类
 */
@Table(name = "s_message")
public class Message implements Serializable {
    @Id
    private Integer id;
    private String title;
    private String brief;
    private String thumbnail;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    private Integer focus;

    public Message() {
    }

    public Message(String title, String brief, String thumbnail, Date modifyTime, Integer focus) {
        this.title = title;
        this.brief = brief;
        this.thumbnail = thumbnail;
        this.modifyTime = modifyTime;
        this.focus = focus;
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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer focus) {
        this.focus = focus;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", modifyTime=" + modifyTime +
                ", focus=" + focus +
                '}';
    }
}
