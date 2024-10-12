package com.distraction.s20jam2024.entity;

public class Item extends Entity {

    public enum ItemType {
        CANDLE
    }

    public ItemType itemType;

    public Item(ItemType itemType, float x, float y) {
        this.itemType = itemType;
        this.x = x;
        this.y = y;
    }

}
