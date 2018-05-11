package com.example.sony.unblockme.model;

/**
 * Created by SONY on 5/7/2018.
 */

public class Block {
    private int length;
    private int startX;
    private int startY;
    private String id;

    public Block(int length, int startX, int startY, String type) {
        this.length = length;
        this.startX = startX;
        this.startY = startY;
        this.id = type;
    }

    public Block() {
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Block{" +
                "length=" + length +
                ", startX=" + startX +
                ", startY=" + startY +
                ", id='" + id + '\'' +
                '}';
    }
}
