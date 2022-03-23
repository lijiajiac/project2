package com.yida.project.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 投诉和建议对应javabean类
 */
@Table(name = "s_servetwo")
public class Servetwo implements Serializable {
    @Id
    private Integer id;
    private String feedback;
    private int readss;
    private String tel;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date feedbackTime;

    public Servetwo() {
    }

    public Servetwo(String feedback, int readss, String tel, Date feedbackTime) {
        this.feedback = feedback;
        this.readss = readss;
        this.tel = tel;
        this.feedbackTime = feedbackTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getReadss() {
        return readss;
    }

    public void setReadss(int readss) {
        this.readss = readss;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    @Override
    public String toString() {
        return "Servetwo{" +
                "id=" + id +
                ", feedback='" + feedback + '\'' +
                ", readss=" + readss +
                ", tel='" + tel + '\'' +
                ", feedbackTime=" + feedbackTime +
                '}';
    }
}
