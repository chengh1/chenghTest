package com.chengh.db.entity;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @program: dbTest
 * @description:
 * @author: chengh
 * @create: 2019-07-22 10:46
 */
public class Order implements Delayed {

    private String orderNo;
    private String receiveName;
    private int cost;
    private String status;
    private Date createTime;
    private Date cancelTime;


    public Order(String orderNo, String receiveName, int cost, String status, Date createTime, Date cancelTime) {
        this.orderNo = orderNo;
        this.receiveName = receiveName;
        this.cost = cost;
        this.status = status;
        this.createTime = createTime;
        this.cancelTime = cancelTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        //下面用到unit.convert()方法，其实在这个小场景不需要用到，只是学习一下如何使用罢了
        long l = unit.convert(cancelTime.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        return l;
    }

    @Override
    public int compareTo(Delayed o) {
        //这里根据取消时间来比较，如果取消时间小的，就会优先被队列提取出来
        return this.getCancelTime().compareTo(((Order) o).getCancelTime());
    }
}
