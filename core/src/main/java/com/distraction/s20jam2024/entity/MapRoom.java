package com.distraction.s20jam2024.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.s20jam2024.Context;
import com.distraction.s20jam2024.Utils;

public class MapRoom extends Entity {

    private int roomType;

    public MapRoom(Context context) {
        this(context, 0);
    }

    public MapRoom(Context context, int roomType) {
        super(context);
        setRoomType(roomType);
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
        if (roomType > 0) {
            setImage(context.getImage("map" + roomType));
        }
    }

    public boolean isStart() {
        return roomType >= 7 && roomType <= 10;
    }

    public boolean isEnd() {
        return roomType >= 11 && roomType <= 14;
    }

    public void randomize() {
        if (roomType >= 1 && roomType <= 4) {
            setRoomType(Utils.nextInt(4) + 1);
        } else if (roomType == 5 || roomType == 6) {
            setRoomType(Utils.nextInt(2) + 5);
        }
    }

    public void cycle() {
        if (roomType == 1) setRoomType(2);
        else if (roomType == 2) setRoomType(3);
        else if (roomType == 3) setRoomType(4);
        else if (roomType == 4) setRoomType(1);
        else if (roomType == 5) setRoomType(6);
        else if (roomType == 6) setRoomType(5);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        if (roomType > 0) Utils.drawCentered(sb, image, x, y);
    }
}
