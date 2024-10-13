package com.distraction.s20jam2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.distraction.s20jam2024.Context;
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

    public PlayScreen(Context context, int index) {
        super(context);

        tiledMap = context.getTiles();
        for (int i = 0; i < tiledMap.getLayers().size(); i++) {
            tiledMap.getLayers().get(i).setVisible(true);
        }
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        renderLayer[0] = index;
        MapGroupLayer groupLayer = (MapGroupLayer) tiledMap.getLayers().get("room" + (renderLayer[0] + 1));
        MapLayer collisionLayer = groupLayer.getLayers().get("collision");
        MapLayer itemLayer = groupLayer.getLayers().get("items");

        for (MapObject object : collisionLayer.getObjects()) {
            MapProperties props = object.getProperties();
            float w = props.get("width", Float.class);
            float h = props.get("height", Float.class);
            float x = props.get("x", Float.class) + w / 2;
            float y = props.get("y", Float.class) + h / 2;
            walls.add(new Entity(context, x, y, w, h));
        }
        for (Entity wall : walls) {
            wall.debug = true;
        }

        for (MapObject object : itemLayer.getObjects()) {
            MapProperties props = object.getProperties();
            items.add(new Item(
                context,
                Item.ItemType.parse(props.get("type", String.class)),
                props.get("x", Float.class),
                props.get("y", Float.class)
            ));
        }

        player = new Player(context);
    }

    @Override
    public void update(float dt) {
        player.left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        player.right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) player.jump();

        player.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render(renderLayer);
        for (Item item : items) item.render(sb);
        player.render(sb);
        for (Entity wall : walls) wall.render(sb);
        sb.end();
    }

}
