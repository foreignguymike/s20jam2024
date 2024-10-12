package com.distraction.s20jam2024.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.s20jam2024.Context;

public abstract class Screen {

    protected Context context;

    protected Screen(Context context) {
        this.context = context;
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);

}
