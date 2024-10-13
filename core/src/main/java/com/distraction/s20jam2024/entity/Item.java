package com.distraction.s20jam2024.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.s20jam2024.Context;

import java.util.Objects;

public class Item extends Entity {

    public enum ItemType {
        CANDLE("candle"),
        START("start"),
        END("end");

        public final String key;

        ItemType(String key) {
            this.key = key;
        }

        public static ItemType parse(String type) {
            for (ItemType itemType : values()) {
                if (Objects.equals(itemType.key, type)) {
                    return itemType;
                }
            }
            throw new IllegalArgumentException("unknown item " + type);
        }
    }

    public ItemType itemType;

    public Item(Context context, ItemType itemType, float x, float y) {
        super(context);
        this.itemType = itemType;
        this.x = x;
        this.y = y;

        setImage(context.getImage(itemType.key));
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(image, x - w / 2, y - h / 2);
    }
}
