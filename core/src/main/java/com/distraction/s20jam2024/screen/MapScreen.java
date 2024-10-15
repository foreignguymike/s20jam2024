package com.distraction.s20jam2024.screen;

import static com.distraction.s20jam2024.Room.ED;
import static com.distraction.s20jam2024.Room.EL;
import static com.distraction.s20jam2024.Room.ER;
import static com.distraction.s20jam2024.Room.EU;
import static com.distraction.s20jam2024.Room.LD;
import static com.distraction.s20jam2024.Room.LR;
import static com.distraction.s20jam2024.Room.LU;
import static com.distraction.s20jam2024.Room.RD;
import static com.distraction.s20jam2024.Room.RU;
import static com.distraction.s20jam2024.Room.SD;
import static com.distraction.s20jam2024.Room.SL;
import static com.distraction.s20jam2024.Room.SR;
import static com.distraction.s20jam2024.Room.SU;
import static com.distraction.s20jam2024.Room.UD;
import static com.distraction.s20jam2024.screen.MapScreen.Direction.DOWN;
import static com.distraction.s20jam2024.screen.MapScreen.Direction.LEFT;
import static com.distraction.s20jam2024.screen.MapScreen.Direction.RIGHT;
import static com.distraction.s20jam2024.screen.MapScreen.Direction.UP;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.s20jam2024.Context;
import com.distraction.s20jam2024.Utils;
import com.distraction.s20jam2024.entity.MapRoom;

import java.util.ArrayList;
import java.util.List;

public class MapScreen extends Screen {

    enum Direction {
        RIGHT(0),
        UP(1),
        LEFT(2),
        DOWN(3);

        final int value;

        Direction(int value) {
            this.value = value;
        }
    }

    private static final int MAX_LENGTH = 8;

    private final TextureRegion bg;

    private MapRoom[][] map;
    private int currentRow;
    private int currentCol;

    private Direction currentDirection;
    private Direction previousDirection;
    private int length;

    private boolean fixed = false;

    public MapScreen(Context context) {
        super(context);
        bg = context.getImage("mapbook");

        generatePath();
    }

    /**
     * RULES:
     * The path must have a length of MAX_LENGTH. (adjustable, need to test to make 20 seconds difficult)
     * <p>
     * Algo:
     * - randomly pick a start
     * - randomly generate valid next rooms and accumulate weight
     * - once weight 10 is reached, next room should be end
     */
    private void generatePath() {
        map = new MapRoom[5][5];
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                map[row][col] = new MapRoom(context);
                map[row][col].x = 193 + col * 16;
                map[row][col].y = 88 + row * 16;
            }
        }
        currentRow = currentCol = 0;
        currentDirection = previousDirection = null;
        length = 0;
        generateStart();
        while (length <= MAX_LENGTH) {
            generateRoom();
        }
        randomizePath();
        randomizeMap();
        fixed = false;
        fixed = checkFixed();
    }

    private void generateStart() {
        int index = Utils.nextInt(map.length * map[0].length);
        int numCols = map[0].length;
        currentRow = index / numCols;
        currentCol = index % numCols;

        List<Direction> validDirections = getValidDirections(null);
        currentDirection = validDirections.get(Utils.nextInt(validDirections.size()));

        int cell;
        if (currentDirection == RIGHT) cell = SR;
        else if (currentDirection == UP) cell = SU;
        else if (currentDirection == LEFT) cell = SL;
        else cell = SD;

        map[currentRow][currentCol].setRoomType(cell);
    }

    private void generateRoom() {
        if (currentDirection == RIGHT) currentCol++;
        if (currentDirection == LEFT) currentCol--;
        if (currentDirection == UP) currentRow++;
        if (currentDirection == DOWN) currentRow--;

        List<Direction> validDirections = getValidDirections(previousDirection);
        previousDirection = currentDirection;
        if (validDirections.isEmpty()) {
            generatePath(); // if run into dead end, just start over
            return;
        }

        currentDirection = validDirections.get(Utils.nextInt(validDirections.size()));

        int cell;
        if (length < MAX_LENGTH) {
            if (previousDirection == RIGHT) {
                if (currentDirection == UP) cell = LU;
                else if (currentDirection == RIGHT) cell = LR;
                else cell = LD;
            } else if (previousDirection == LEFT) {
                if (currentDirection == UP) cell = RU;
                else if (currentDirection == LEFT) cell = LR;
                else cell = RD;
            } else if (previousDirection == UP) {
                if (currentDirection == LEFT) cell = LD;
                else if (currentDirection == UP) cell = UD;
                else cell = RD;
            } else {
                if (currentDirection == LEFT) cell = LU;
                else if (currentDirection == DOWN) cell = UD;
                else cell = RU;
            }
        } else {
            if (previousDirection == RIGHT) cell = ER;
            else if (previousDirection == UP) cell = EU;
            else if (previousDirection == LEFT) cell = EL;
            else cell = ED;
        }

        map[currentRow][currentCol].setRoomType(cell);

        length++;
    }

    private List<Direction> getValidDirections(Direction previousDirection) {
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

        if (currentDirection == RIGHT && previousDirection == RIGHT) validDirections.remove(RIGHT);
        if (currentDirection == LEFT && previousDirection == LEFT) validDirections.remove(LEFT);
        if (currentDirection == UP && previousDirection == UP) validDirections.remove(UP);
        if (currentDirection == DOWN && previousDirection == DOWN) validDirections.remove(DOWN);

        if (validDirections.contains(RIGHT) && map[currentRow][currentCol + 1].getRoomType() != 0)
            validDirections.remove(RIGHT);
        if (validDirections.contains(LEFT) && map[currentRow][currentCol - 1].getRoomType() != 0)
            validDirections.remove(LEFT);
        if (validDirections.contains(UP) && map[currentRow + 1][currentCol].getRoomType() != 0)
            validDirections.remove(UP);
        if (validDirections.contains(DOWN) && map[currentRow - 1][currentCol].getRoomType() != 0)
            validDirections.remove(DOWN);

        return validDirections;
    }

    private void randomizePath() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                map[row][col].randomize();
            }
        }
    }

    private void randomizeMap() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col].getRoomType() == 0) {
                    map[row][col].setRoomType(Utils.nextInt(5) + 1);
                }
            }
        }
    }

    private boolean checkFixed() {
        if (fixed) return true;
        int testRow = -1;
        int testCol = -1;
        Direction next = null;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                int roomType = map[row][col].getRoomType();
                if (roomType == 7) next = RIGHT;
                if (roomType == 8) next = UP;
                if (roomType == 9) next = LEFT;
                if (roomType == 10) next = DOWN;
                if (map[row][col].isStart()) {
                    testRow = row;
                    testCol = col;
                    break;
                }
            }
        }
        if (testRow == -1 || testCol == -1 || next == null) return false;

        while (!map[testRow][testCol].isEnd()) {
            if (next == RIGHT) testCol++;
            if (next == LEFT) testCol--;
            if (next == UP) testRow++;
            if (next == DOWN) testRow--;
            if (testRow < 0 || testCol < 0 || testRow == map.length || testCol == map[0].length)
                return false;
            if (map[testRow][testCol] == null) return false;
            int roomType = map[testRow][testCol].getRoomType();
            if (map[testRow][testCol].isEnd()) {
                if (next == RIGHT && roomType == 11) return true;
                else if (next == UP && roomType == 12) return true;
                else if (next == LEFT && roomType == 13) return true;
                else return next == DOWN && roomType == 14;
            }
            if (next == RIGHT) {
                if (roomType == 1) next = UP;
                else if (roomType == 4) next = DOWN;
                else if (roomType != 5) return false;
            } else if (next == LEFT) {
                if (roomType == 2) next = UP;
                else if (roomType == 3) next = DOWN;
                else if (roomType != 5) return false;
            } else if (next == UP) {
                if (roomType == 3) next = RIGHT;
                else if (roomType == 4) next = LEFT;
                else if (roomType != 6) return false;
            } else if (next == DOWN) {
                if (roomType == 1) next = LEFT;
                else if (roomType == 2) next = RIGHT;
                else if (roomType != 6) return false;
            } else {
                return false;
            }
        }
        return true;
    }

    private int[][] toMap() {
        int[][] ret = new int[map.length][map[0].length];
        for (int row = 0; row < ret.length; row++) {
            for (int col = 0; col < ret[0].length; col++) {
                ret[row][col] = map[row][col].getRoomType();
            }
        }
        return ret;
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            generatePath();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            context.sm.push(new PlayScreen(context, Utils.flip(toMap())));
        }

        unprojectMouse();
        if (Gdx.input.justTouched() && !fixed) {
            for (int row = 0; row < map.length; row++) {
                for (int col = 0; col < map[0].length; col++) {
                    if (map[row][col].contains(m.x, m.y)) {
                        map[row][col].cycle();
                        fixed = checkFixed();
                        System.out.println("fixed: " + fixed);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(bg, 0, 0);
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                map[row][col].render(sb);
            }
        }
        sb.end();
    }
}
