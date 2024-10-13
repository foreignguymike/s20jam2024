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
     *
     * 7 - start right
     * 8 - start up
     * 9 - start left
     * 10 - start down
     *
     * 11 - end right
     * 12 - end up
     * 13 - end left
     * 14 - end down
     */

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
