package com.distraction.s20jam2024.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.s20jam2024.Context;
import com.distraction.s20jam2024.Utils;

public class Player extends Entity {

    private static final int SPEED = 150;
    private static final int GRAV = -900;
    private static final int JUMP = 450;

    public boolean left = false;
    public boolean right = false;

    public Player(Context context) {
        super(context);
        x = 50;
        y = 120;
        w = 30;
        h = 30;
        debug = true;
    }

    public void jump() {
        dy = JUMP;
    }

    @Override
    public void update(float dt) {
        if (left && right) dx = 0;
        else if (left) dx = -SPEED;
        else if (right) dx = SPEED;
        else dx = 0;

        dy += GRAV * dt;

        x += dx * dt;
        y += dy * dt;
    }

    @Override
    public void render(SpriteBatch sb) {
        Utils.drawCentered(sb, pixel, x, y, w, h);
    }
}
