package com.distraction.s20jam2024.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.distraction.s20jam2024.Constants;
import com.distraction.s20jam2024.Context;

public abstract class Screen {

    protected final Context context;
    protected final OrthographicCamera cam;
    protected final Vector3 m;

    protected Screen(Context context) {
        this.context = context;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);
        m = new Vector3();
    }

    protected void unprojectMouse() {
        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(m);
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);

}
