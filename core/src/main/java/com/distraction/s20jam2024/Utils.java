package com.distraction.s20jam2024;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y) {
        sb.draw(image, x - image.getRegionWidth() / 2f, y - image.getRegionHeight() / 2f);
    }

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y, float w, float h) {
        sb.draw(image, x - w / 2, y - h / 2, w, h);
    }

    public static int[][] flip(int[][] arr) {
        int[][] ret = new int[arr.length][arr[0].length];
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[row].length; col++) {
                ret[arr.length - 1 - row][col] = arr[row][col];
            }
        }
        return ret;
    }

}
