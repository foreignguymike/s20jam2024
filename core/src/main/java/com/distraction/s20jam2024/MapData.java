package com.distraction.s20jam2024;

import com.distraction.s20jam2024.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapData {

    public static class Room {

        public int[][] tiles;
        public List<Item> items;

        public Room(int[][] tiles, List<Item> items) {
            this.tiles = tiles;
            this.items = items;
        }

    }

    public static Random random = new Random();
    public static List<Room> rooms;
    private static final int N = -1;

    public static Room getRandomRoom() {
        return rooms.get(random.nextInt(rooms.size()));
    }

    static {
        rooms = new ArrayList<>();
        rooms.add(new Room(
            new int[][] {
                { 9, 10, N, N, N, N, 8, 9 },
                { N, N, N, N, N, N, N, N },
                { N, N, N, N, N, N, N, N },
                { N, N, N, 0, 2, N, N, N },
                { N, N, N, N, N, N, N, N },
                { 1, 2, N, N, N, N, 0, 1 },
            },
            List.of(
                new Item(Item.ItemType.CANDLE, 40, 120),
                new Item(Item.ItemType.CANDLE, 160, 80),
                new Item(Item.ItemType.CANDLE, 280, 120)
            )
        ));
    }

}
