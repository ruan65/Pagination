package com.cool.example.pagination.data;

/**
 * Created by a on 08/11/2016.
 */

public class Item {

    private int id;
    private String itemStr;

    public Item(int id, String itemStr) {
        this.id = id;
        this.itemStr = itemStr;
    }

    public int getId() {
        return id;
    }

    public String getItemStr() {
        return itemStr;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemStr='" + itemStr + '\'' +
                '}';
    }
}
