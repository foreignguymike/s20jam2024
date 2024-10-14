package com.distraction.s20jam2024;

import com.distraction.s20jam2024.entity.Entity;
import com.distraction.s20jam2024.entity.Item;

import java.util.List;

public class Room {

    /**
     * Room types:
     * 1 - left up
     * 2 - up right
     * 3 - down right
     * 4 - left down
     * 5 - left right
     * 6 - up down
     * 7 - start right
     * 8 - start up
     * 9 - start left
     * 10 - start down
     * 11 - end right
     * 12 - end up
     * 13 - end left
     * 14 - end down
     */

    public static final int LU = 1;
    public static final int RU = 2;
    public static final int RD = 3;
    public static final int LD = 4;
    public static final int LR = 5;
    public static final int UD = 6;

    public static final int SR = 7;
    public static final int SU = 8;
    public static final int SL = 9;
    public static final int SD = 10;

    public static final int ER = 11;
    public static final int EU = 12;
    public static final int EL = 13;
    public static final int ED = 14;


    public final int type;
    public final int index;
    public final List<Entity> walls;
    public final List<Item> items;

    public Room(int type, int index, List<Entity> walls, List<Item> items) {
        this.type = type;
        this.index = index;
        this.walls = walls;
        this.items = items;
    }
}
