package com.distraction.s20jam2024;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.distraction.s20jam2024.screen.PlayScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {

    private Context context;
    private SpriteBatch sb;

    @Override
    public void create() {
        context = new Context();
        context.sm.push(new PlayScreen(context));
        sb = new SpriteBatch();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        context.sm.update(Gdx.graphics.getDeltaTime());
        context.sm.render(sb);
    }

    @Override
    public void dispose() {
        sb.dispose();
    }
}
