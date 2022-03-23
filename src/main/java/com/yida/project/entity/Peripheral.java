package com.yida.project.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 周边列表javabean类
 */
@Table(name = "s_peripheral")
public class Peripheral implements Serializable {
    @Id
    private Integer id;
    //标题
    private String title;
    //所属类型
    private Classify classify;
    //住所类型
    private String stay;
    //交通信息
    private String traffic;
    //参考价格
    private Integer price;
    //推荐菜
    private String recommendation;
    //地址
    private String city;
    //地图显示名称
    private String territory;
    //经度纬度
    private String longitude;
    private String latitude;
    //联系电话1,2
    private String tho;
    private String tho2;
    //简介
    private String brief;
    //上传图片
    private String spotImages;
    //缩虐图
    private String thumbnail;
    //修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ModifyTime;

    public Peripheral() {
    }

    public Peripheral(String title, Classify classify, String stay, String traffic, Integer price, String recommendation, String city, String territory, String longitude, String latitude, String tho, String tho2, String brief, String spotImages, String thumbnail, Date modifyTime) {
        this.title = title;
        this.classify = classify;
        this.stay = stay;
        this.traffic = traffic;
        this.price = price;
        this.recommendation = recommendation;
        this.city = city;
        this.territory = territory;
        this.longitude = longitude;
        this.latitude = latitude;
        this.tho = tho;
        this.tho2 = tho2;
        this.brief = brief;
        this.spotImages = spotImages;
        this.thumbnail = thumbnail;
        ModifyTime = modifyTime;
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

    public Classify getClassify() {
        return classify;
    }

    public void setClassify(Classify classify) {
        this.classify = classify;
    }

    public String getStay() {
        return stay;
    }

    public void setStay(String stay) {
        this.stay = stay;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Date getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        ModifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "Peripheral{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", classify=" + classify +
                ", stay='" + stay + '\'' +
                ", traffic='" + traffic + '\'' +
                ", price=" + price +
                ", recommendation='" + recommendation + '\'' +
                ", city='" + city + '\'' +
                ", territory='" + territory + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", tho='" + tho + '\'' +
                ", tho2='" + tho2 + '\'' +
                ", brief='" + brief + '\'' +
                ", spotImages='" + spotImages + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", ModifyTime=" + ModifyTime +
                '}';
    }
}
