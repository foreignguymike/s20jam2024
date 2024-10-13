package com.distraction.s20jam2024.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.s20jam2024.Context;
import com.distraction.s20jam2024.Utils;

import java.util.List;

public class Player extends Entity {

    private static final int SPEED = 180;
    private static final int GRAV = -1500;
    private static final int JUMP = 550;
    private static final int MAX_FALL = -400;

    public boolean left = false;
    public boolean right = false;

    private final List<Entity> walls;

    private boolean onGround = false;
    private boolean usedExtraJump = false;

    public Player(Context context, List<Entity> walls) {
        super(context);
        this.walls = walls;

        w = 30;
        h = 30;

        debug = true;
    }

    public void jump() {
        if (onGround) {
            dy = JUMP;
        } else if (!usedExtraJump) {
            dy = JUMP;
            usedExtraJump = true;
        }
    }

    @Override
    public void update(float dt) {
        if (left && right) dx = 0;
        else if (left) dx = -SPEED;
        else if (right) dx = SPEED;
        else dx = 0;

        for (Entity wall : walls) {
            if (wall.overlaps(x + dx * dt, y, w, h)) {
                if (dx > 0) {
                    x = wall.x - wall.w / 2 - w / 2;
                } else if (dx < 0) {
                    x = wall.x + wall.w / 2 + w / 2;
                }
                dx = 0;
                break;
            }
        }

        dy += GRAV * dt;
        if (dy < MAX_FALL) dy = MAX_FALL;
        onGround = false;
        for (Entity wall : walls) {
            if (wall.overlaps(x, y + dy * dt, w, h)) {
                if (dy > 0) {
                    y = wall.y - wall.h / 2 - h / 2;
                } else if (dy < 0) {
                    y = wall.y + wall.h / 2 + h / 2;
                    onGround = true;
                    usedExtraJump = false;
                }
                dy = 0;
                break;
            }
        }

        x += dx * dt;
        y += dy * dt;
    }

    @Override
    public void render(SpriteBatch sb) {
        Utils.drawCentered(sb, pixel, x, y, w, h);
    }
}
