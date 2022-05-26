package com.classes.objects;

public class Item {
    private String name, lastMsg;
    private int image;

    public Item(String name, String lastMsg, int image) {
        this.name = name;
        this.lastMsg = lastMsg;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
