package com.distraction.s20jam2024.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.s20jam2024.Constants;
import com.distraction.s20jam2024.Context;

public abstract class Screen {

    protected Context context;
    protected OrthographicCamera cam;

    protected Screen(Context context) {
        this.context = context;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);

}
