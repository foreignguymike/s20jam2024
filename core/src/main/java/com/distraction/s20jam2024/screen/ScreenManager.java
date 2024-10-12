package com.distraction.s20jam2024.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class ScreenManager {

    public Stack<Screen> screens;

    public ScreenManager() {
        screens = new Stack<>();
    }

    public void push(Screen screen) {
        screens.push(screen);
    }

    public Screen pop() {
        return screens.pop();
    }

    public Screen replace(Screen screen) {
        Screen oldScreen = pop();
        push(screen);
        return oldScreen;
    }

    public void update(float dt) {
        screens.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        screens.peek().render(sb);
    }

}
