package com.yida.project.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "s_setup_1")
public class SetupTwo implements Serializable {
    @Id
    private Integer id;
    private String title;
    private String birth;
    private String imgs;

    public SetupTwo() {
    }

    public SetupTwo(String title, String birth, String imgs) {
        this.title = title;
        this.birth = birth;
        this.imgs = imgs;
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

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "SetupTwo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", birth='" + birth + '\'' +
                ", imgs='" + imgs + '\'' +
                '}';
    }
}
