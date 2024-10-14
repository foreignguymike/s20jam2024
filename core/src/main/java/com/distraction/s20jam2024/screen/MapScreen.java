package com.distraction.s20jam2024.screen;

import static com.distraction.s20jam2024.screen.MapScreen.Direction.*;
import static com.distraction.s20jam2024.Room.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.s20jam2024.Context;
import com.distraction.s20jam2024.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapScreen extends Screen {

    enum Direction {
        RIGHT(0),
        UP(1),
        LEFT(2),
        DOWN(3);

        int value;

        Direction(int value) {
            this.value = value;
        }
    }

    private static final int MAX_WEIGHT = 7;

    private final Random random = new Random();

    private final TextureRegion bg;
    private final TextureRegion[] rooms;

    private int[][] map;
    private int currentRow;
    private int currentCol;
    private Direction currentDirection;
    private int weight;

    public MapScreen(Context context) {
        super(context);
        bg = context.getImage("mapbook");
        map = new int[5][5];
        rooms = context.getImages("map");

        generatePath();
    }

    /**
     * RULES:
     * The path must have a weight of 10. (adjustable, need to test to make 20 seconds difficult)
     * Path going upward in a UD cell has weight 3.
     * Path going downward in a UD cell has weight 1.
     * All other paths have weight 2.
     * <p>
     * Algo:
     * - randomly pick a start
     * - randomly generate valid next rooms and accumulate weight
     * - once weight 10 is reached, next room should be end
     */
    private void generatePath() {
        map = new int[5][5];
        currentRow = currentCol = 0;
        currentDirection = null;
        weight = 0;
        generateStart();
        while (weight <= MAX_WEIGHT) {
            generateRoom();
        }
    }

    private void generateStart() {
        int index = random.nextInt(map.length * map[0].length);
        int numCols = map[0].length;
        currentRow = index / numCols;
        currentCol = index % numCols;

        List<Direction> validDirections = getValidDirections();
        currentDirection = validDirections.get(random.nextInt(validDirections.size()));

        int cell;
        if (currentDirection == RIGHT) cell = SR;
        else if (currentDirection == UP) cell = SU;
        else if (currentDirection == LEFT) cell = SL;
        else cell = SD;

        map[currentRow][currentCol] = cell;
    }

    private void generateRoom() {
        if (currentDirection == RIGHT) currentCol++;
        if (currentDirection == LEFT) currentCol--;
        if (currentDirection == UP) currentRow++;
        if (currentDirection == DOWN) currentRow--;

        List<Direction> validDirections = getValidDirections();
        if (validDirections.isEmpty()) {
            generatePath(); // if run into dead end, just start over
            return;
        }

        Direction newDirection = validDirections.get(random.nextInt(validDirections.size()));

        int cell;
        if (weight < MAX_WEIGHT) {
            if (currentDirection == RIGHT) {
                if (newDirection == UP) cell = LU;
                else if (newDirection == RIGHT) cell = LR;
                else cell = LD;
            } else if (currentDirection == LEFT) {
                if (newDirection == UP) cell = RU;
                else if (newDirection == LEFT) cell = LR;
                else cell = RD;
            } else if (currentDirection == UP) {
                if (newDirection == LEFT) cell = LD;
                else if (newDirection == UP) cell = UD;
                else cell = RD;
            } else {
                if (newDirection == LEFT) cell = LU;
                else if (newDirection == DOWN) cell = UD;
                else cell = RU;
            }
        } else {
            if (currentDirection == RIGHT) cell = ER;
            else if (currentDirection == UP) cell = EU;
            else if (currentDirection == LEFT) cell = EL;
            else cell = ED;
        }

        currentDirection = newDirection;
        map[currentRow][currentCol] = cell;

        weight++;
    }

    private List<Direction> getValidDirections() {
        int numRows = map.length;
        int numCols = map[0].length;
        List<Direction> validDirections = new ArrayList<>();
        validDirections.add(RIGHT);
        validDirections.add(UP);
        validDirections.add(LEFT);
        validDirections.add(DOWN);

        if (currentRow == 0) validDirections.remove(DOWN);
        if (currentRow == numRows - 1) validDirections.remove(UP);
        if (currentCol == 0) validDirections.remove(LEFT);
        if (currentCol == numCols - 1) validDirections.remove(RIGHT);

        if (currentDirection == RIGHT) validDirections.remove(LEFT);
        if (currentDirection == LEFT) validDirections.remove(RIGHT);
        if (currentDirection == UP) validDirections.remove(DOWN);
        if (currentDirection == DOWN) validDirections.remove(UP);

        if (validDirections.contains(RIGHT) && map[currentRow][currentCol + 1] != 0) validDirections.remove(RIGHT);
        if (validDirections.contains(LEFT) && map[currentRow][currentCol - 1] != 0) validDirections.remove(LEFT);
        if (validDirections.contains(UP) && map[currentRow + 1][currentCol] != 0) validDirections.remove(UP);
        if (validDirections.contains(DOWN) && map[currentRow - 1][currentCol] != 0) validDirections.remove(DOWN);

        return validDirections;
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            generatePath();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            context.sm.push(new PlayScreen(context, Utils.flip(map)));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(bg, 0, 0);
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                int r = map[row][col];
                if (r > 0) {
                    sb.draw(rooms[r - 1], 186 + col * 16, 81 + row * 16);
                }
            }
        }
        sb.end();
    }
}
