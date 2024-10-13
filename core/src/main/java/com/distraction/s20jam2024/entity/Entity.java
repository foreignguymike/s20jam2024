package com.distraction.s20jam2024.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.s20jam2024.Animation;
import com.distraction.s20jam2024.Context;

public class Entity {

    protected Context context;

    public float x;
    public float y;
    public float w;
    public float h;

    public float dx;
    public float dy;

    protected Animation animation;
    protected TextureRegion image;
    protected TextureRegion pixel;

    public boolean debug = false;

    public Entity(Context context) {
        this(context, 0, 0, 0, 0);
    }

    public Entity(Context context, float x, float y, float w, float h) {
        this.context = context;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        pixel = context.getPixel();
    }

    public void setImage(TextureRegion image) {
        this.image = image;
        w = image.getRegionWidth();
        h = image.getRegionHeight();
    }

    public boolean overlaps(float x, float y, float w, float h) {
        return this.x - this.w / 2 < x + w / 2
            && this.x + this.w / 2 > x - w / 2
            && this.y - this.h / 2 < y + h / 2
            && this.y + this.h / 2 > y - h / 2;
    }

    public void update(float dt) {
    }

    public void render(SpriteBatch sb) {
        if (debug) {
            sb.draw(pixel, x - w / 2, y - h / 2, w, 1);
            sb.draw(pixel, x - w / 2, y + h / 2, w, 1);
            sb.draw(pixel, x - w / 2, y - h / 2, 1, h);
            sb.draw(pixel, x + w / 2, y - h / 2, 1, h);
        }
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + w + ", " + h + "]";
    }
}
