package com.distraction.s20jam2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.distraction.s20jam2024.Constants;
import com.distraction.s20jam2024.Context;
import com.distraction.s20jam2024.Room;
import com.distraction.s20jam2024.Utils;
import com.distraction.s20jam2024.entity.Entity;
import com.distraction.s20jam2024.entity.Item;
import com.distraction.s20jam2024.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayScreen extends Screen {

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private int[] renderLayer = new int[]{0};

    private List<Item> items = new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private Player player;

    private OrthographicCamera tileCam;
    private Room[][] rooms;

    private int roomRow;
    private int roomCol;
    private int roomLeft;
    private int roomRight;
    private int roomTop;
    private int roomBottom;

    private Vector3 mouse = new Vector3();

    public PlayScreen(Context context, int[][] maze) {
        super(context);
        tileCam = new OrthographicCamera();
        tileCam.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);

        player = new Player(context, walls);
        player.x = 320 + 120;
        player.y = 240 + 240 + 200;

        parseMaze(maze);
    }

    private void parseMaze(int[][] maze) {
        rooms = new Room[maze.length][maze[0].length];

        maze = Utils.flip(maze);

        tiledMap = context.getTiles();
        for (int i = 0; i < tiledMap.getLayers().size(); i++) {
            tiledMap.getLayers().get(i).setVisible(true);
        }
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                int roomType = maze[row][col];
                if (roomType > 0) {
                    MapGroupLayer groupLayer = (MapGroupLayer) tiledMap.getLayers().get("room" + roomType);
                    MapLayer collisionLayer = groupLayer.getLayers().get("collision");
                    MapLayer itemLayer = groupLayer.getLayers().get("items");

                    List<Entity> walls = new ArrayList<>();
                    for (MapObject object : collisionLayer.getObjects()) {
                        MapProperties props = object.getProperties();
                        float w = props.get("width", Float.class);
                        float h = props.get("height", Float.class);
                        float x = props.get("x", Float.class) + w / 2 + col * Constants.WIDTH;
                        float y = props.get("y", Float.class) + h / 2 + row * Constants.HEIGHT;
                        walls.add(new Entity(context, x, y, w, h));
                    }
                    for (Entity wall : walls) {
                        wall.debug = true;
                    }

                    List<Item> items = new ArrayList<>();
                    for (MapObject object : itemLayer.getObjects()) {
                        MapProperties props = object.getProperties();
                        items.add(new Item(
                            context,
                            Item.ItemType.parse(props.get("type", String.class)),
                            props.get("x", Float.class) + col * Constants.WIDTH,
                            props.get("y", Float.class) + row * Constants.HEIGHT
                        ));
                    }
                    rooms[row][col] = new Room(roomType, walls, items);

                    this.walls.addAll(walls);
                    this.items.addAll(items);
                }
            }
        }

        setRoom(2, 1);
    }

    private void setRoom(int row, int col) {
        roomRow = row;
        roomCol = col;
        roomLeft = col * Constants.WIDTH;
        roomRight = (col + 1) * Constants.WIDTH;
        roomTop = (row + 1) * Constants.HEIGHT;
        roomBottom = row * Constants.HEIGHT;

        cam.position.set(roomLeft + Constants.WIDTH / 2f, roomBottom + Constants.HEIGHT / 2f, 0);
        cam.update();

        Room room = rooms[row][col];
        renderLayer[0] = room.type - 1;
    }

    @Override
    public void update(float dt) {
        player.left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        player.right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) player.jump();

        mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(mouse);

        if (player.x < roomLeft) {
            setRoom(roomRow, roomCol - 1);
        } else if (player.x > roomRight) {
            setRoom(roomRow, roomCol + 1);
        } else if (player.y < roomBottom) {
            setRoom(roomRow - 1, roomCol);
        } else if (player.y > roomTop) {
            setRoom(roomRow + 1, roomCol);
        }

        player.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        tiledMapRenderer.setView(tileCam);
        tiledMapRenderer.render(renderLayer);

        sb.setProjectionMatrix(cam.combined);
        for (Item item : items) item.render(sb);
        player.render(sb);
        for (Entity wall : walls) wall.render(sb);
        sb.end();
    }

}
