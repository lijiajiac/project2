package com.yida.project.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "s_releasing_notices")
public class ReleasingNotices implements Serializable {
    @Id
    private Integer id;
    //标题
    private String title;
    //所在景区
    private Scenic scenic;
    //游玩时长
    private String time;
    //开放时长
    private String time2;
    //交通信息
    private String traffic;
    //门表   成人
    private Integer adult;
    // 儿童
    private Integer child;
    //地址
    private String site;
    // 门票说明
    private String tickets;
    //地图名称
    private String territory;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //联系电话1
    private String tho;
    //电话2
    private String tho2;
    //简介
    private String brief;
    //img
    private String spotImages;
    //修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ModifyTime;

    //缩虐图
    private String thumbnail;

    public ReleasingNotices() {
    }

    public ReleasingNotices(String title, Scenic scenic, String time, String time2, String traffic, Integer adult, Integer child, String site, String tickets, String territory, String longitude, String latitude, String tho, String tho2, String brief, String spotImages, Date modifyTime, String thumbnail) {
        this.title = title;
        this.scenic = scenic;
        this.time = time;
        this.time2 = time2;
        this.traffic = traffic;
        this.adult = adult;
        this.child = child;
        this.site = site;
        this.tickets = tickets;
        this.territory = territory;
        this.longitude = longitude;
        this.latitude = latitude;
        this.tho = tho;
        this.tho2 = tho2;
        this.brief = brief;
        this.spotImages = spotImages;
        ModifyTime = modifyTime;
        this.thumbnail = thumbnail;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public Integer getAdult() {
        return adult;
    }

    public void setAdult(Integer adult) {
        this.adult = adult;
    }

    public Integer getChild() {
        return child;
    }

    public void setChild(Integer child) {
        this.child = child;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
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

    public String getTho() {
        return tho;
    }

    public void setTho(String tho) {
        this.tho = tho;
    }

    public String getTho2() {
        return tho2;
    }

    public void setTho2(String tho2) {
        this.tho2 = tho2;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getSpotImages() {
        return spotImages;
    }

    public void setSpotImages(String spotImages) {
        this.spotImages = spotImages;
    }

    public Date getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        ModifyTime = modifyTime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "ReleasingNotices{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", time2='" + time2 + '\'' +
                ", traffic='" + traffic + '\'' +
                ", adult=" + adult +
                ", child=" + child +
                ", site='" + site + '\'' +
                ", tickets='" + tickets + '\'' +
                ", territory='" + territory + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", tho='" + tho + '\'' +
                ", tho2='" + tho2 + '\'' +
                ", brief='" + brief + '\'' +
                ", spotImages='" + spotImages + '\'' +
                ", ModifyTime=" + ModifyTime +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
