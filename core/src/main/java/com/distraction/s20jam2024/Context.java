package com.distraction.s20jam2024;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.distraction.s20jam2024.screen.ScreenManager;

public class Context {

    private static final String TILED_FILE = "tiled.tmx";
    private static final String ATLAS_FILE = "s20jam2024.atlas";

    public AssetManager assets;
    public ScreenManager sm;

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS_FILE, TextureAtlas.class);
        assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assets.load(TILED_FILE, TiledMap.class);
        assets.finishLoading();

        sm = new ScreenManager();
    }

    public TiledMap getTiles() {
        return assets.get(TILED_FILE);
    }

    public TextureRegion getImage(String key) {
        TextureRegion region = assets.get(ATLAS_FILE, TextureAtlas.class).findRegion(key);
        if (region == null) throw new IllegalStateException("image " + key + " not found");
        return region;
    }

    public TextureRegion getPixel() {
        return assets.get(ATLAS_FILE, TextureAtlas.class).findRegion("pixel");
    }

}
