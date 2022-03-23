package com.yida.project.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 版本管理javabean类
 */
@Table(name = "s_setup_2")
public class Setup implements Serializable {
    @Id
    private Integer id;
    private String andtype;
    private String version;
    private String city;
    private String myupdate;

    public Setup() {
    }

    public Setup(String andtype, String version, String city, String myupdate) {
        this.andtype = andtype;
        this.version = version;
        this.city = city;
        this.myupdate = myupdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAndtype() {
        return andtype;
    }

    public void setAndtype(String andtype) {
        this.andtype = andtype;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMyupdate() {
        return myupdate;
    }

    public void setMyupdate(String myupdate) {
        this.myupdate = myupdate;
    }

    @Override
    public String toString() {
        return "Setup{" +
                "id=" + id +
                ", andtype='" + andtype + '\'' +
                ", version='" + version + '\'' +
                ", city='" + city + '\'' +
                ", myupdate='" + myupdate + '\'' +
                '}';
    }
}
