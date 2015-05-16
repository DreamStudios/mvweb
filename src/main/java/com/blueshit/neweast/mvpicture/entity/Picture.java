package com.blueshit.neweast.mvpicture.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 图片信息实体类
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/4/22 16:22
 * @description
 */
@Entity
@Table(name = "picture")
@NamedQuery(name = "Picture.findAll", query="SELECT a FROM Picture a")
public class Picture implements Serializable,Cloneable{
	private static final long serialVersionUID = -7269172643125918597L;

	//图片实体主键
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = true)
	private int id;
    
    //图片标题信息
    @Column(nullable = true,length = 255)
    private String title;

    //图片形式(1:自有，2:第三方)
    @Column(nullable = false, columnDefinition = "int(11) default 1")
    private int style;

    //图片分类(10：柔美)
    @Column(nullable = false)
    private int ptype;

    //图片地址
    @Column(nullable = false,length = 255)
    private String url;

    //运行状态(1开启，0暂停)
    @Column(nullable = false, columnDefinition = "int(11) default 0")
    private int status;

    //创建时间
    @Column(nullable = false)
    private Date createTime = new Date();

    //更新时间
    @Column(nullable = false)
    private Date updateTime = new Date();

    //权值(热词下发顺序)
    @Column(nullable = false)
    private int weight = 1;

    //开始投放日期
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startTime = new Date();

    //结束投放日期
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endTime = new Date();

    //投放时间段,为空表示所有时间都投放
    @Column(nullable = true)
    private String hour;

    //是否需要同步(1:是 0:否)
    @Column(nullable = false, columnDefinition = "int(11) default 1")
    private int sync = 1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getPtype() {
        return ptype;
    }

    public void setPtype(int ptype) {
        this.ptype = ptype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }
}
