package com.distraction.s20jam2024;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.distraction.s20jam2024.screen.ScreenManager;

public class Context {

    private static final String TILED_FILE = "tiled.tmx";

    public AssetManager assets;
    public ScreenManager sm;

    public Context() {
        assets = new AssetManager();
        assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assets.load(TILED_FILE, TiledMap.class);
        assets.finishLoading();

        sm = new ScreenManager();
    }

    public TiledMap getTiles() {
        return assets.get(TILED_FILE);
    }

}
