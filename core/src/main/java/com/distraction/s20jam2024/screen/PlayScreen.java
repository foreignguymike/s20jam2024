package com.distraction.s20jam2024.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.distraction.s20jam2024.Context;

public class PlayScreen extends Screen {

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    public PlayScreen(Context context) {
        super(context);

        tiledMap = context.getTiles();
        for (int i = 0; i < tiledMap.getLayers().size(); i++) {
            tiledMap.getLayers().get(i).setVisible(true);
        }
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        MapLayers layers = tiledMap.getLayers();
        for (int i = 0; i < layers.size(); i++) {
            MapGroupLayer group = (MapGroupLayer) layers.get(i);
            System.out.println("Group Data [" + i + "]");
            System.out.println("name: " + group.getName());

            MapLayers groupLayers = group.getLayers();
            for (int j = 0; j < groupLayers.size(); j++) {
                MapLayer layer = groupLayers.get(j);
                System.out.println("    Layer Data [" + j + "]");
                System.out.println("    name: " + layer.getName());
            }
        }
    }

    @Override
    public void update(float dt) {

    }

    private int[] r = new int[] { 0 };

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render(r);
        sb.end();
    }

}
