package com.example.appmusic;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private String caption;
    private int hinhAnh;
    private int songResource;
    private String loi;

    public Item(String name, String caption, int hinhAnh, int songResource,String loi) {
        this.name = name;
        this.caption = caption;
        this.hinhAnh = hinhAnh;
        this.songResource = songResource;
        this.loi = loi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getSongResource() {
        return songResource;
    }

    public void setSongResource(int songResource) {
        this.songResource = songResource;
    }

    public String getLoi() {
        return loi;
    }

    public void setLoi(String loi) {
        this.loi = loi;
    }
}
