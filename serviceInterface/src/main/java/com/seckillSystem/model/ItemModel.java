package com.seckillSystem.model;


import com.seckillSystem.pojo.PromoDO;

import java.io.Serializable;

public class ItemModel implements Serializable {
    private int id;
    private String name;
    private String pictureUrl;
    private double price;
    private PromoDO promoDO;

    public PromoDO getPromoDO() {
        return promoDO;
    }

    public void setPromoDO(PromoDO promoDO) {
        this.promoDO = promoDO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
