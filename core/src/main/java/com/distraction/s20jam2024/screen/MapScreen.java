package com.distraction.s20jam2024.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.s20jam2024.Context;

public class MapScreen extends Screen {

    private TextureRegion bg;

    public MapScreen(Context context) {
        super(context);

        bg = context.getImage("mapbook");
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(bg, 0, 0);
        sb.end();
    }
}
