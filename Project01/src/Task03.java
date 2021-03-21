import processing.core.PApplet;

import java.util.ArrayList;

public class Task03 extends PApplet {

    private ArrayList<int[][]> animations = new ArrayList<>();
    private int size = 100;
    private Game2048 ng = new Game2048(animations);
    private boolean start = false;
    private int bestResult = 0;
    private boolean isLost = false;

    public void settings() {
        fullScreen();
    }

    public void setup() {
        textSize(45);
        strokeWeight(10);
        stroke(242, 168, 7);
        background(0);
        textAlign(CENTER, CENTER);
    }

    public void draw() {

        if (isLost) {
            clearBackground();
            fill(250, 0, 0);
            text("loh ebanyi", width / 2.0f, height / 2.0f);
        } else {
            if (start) {
                clearBackground();
                setBackground();
                drawGrid();
            }
        }
    }


    private void drawGrid() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if (ng.area[i - 1][j - 1] != 0) {
                    int exp = Math.getExponent(ng.area[i - 1][j - 1] - 1);
                    fill(20 * exp + 20 * exp, 0, 255 - 20 * exp);
                    rect(size * j + width / 4.0f, size * i + height / 8.0f, size, size);
                    fill(252, 94, 3);
                    text(ng.area[i - 1][j - 1], size * j + width / 4.0f + size / 2.0f, size * i + height / 8.0f + size / 2.0f);
                } else {
                    fill(100, 100, 100);
                    rect(size * j + width / 4.0f, size * i + height / 8.0f, size, size);
                }
            }
        }
    }

    private void clearBackground() {
        fill(0);
        push();
        noStroke();
        rect(0, 0, width, height);
        pop();
    }

    private void setBackground() {
        textSize(70);
        fill(240, 46, 214);
        text("Game: 2048", width / 2.0f - size / 4.0f, height / 8.0f);
        fill(242, 242, 44);
        textSize(35);
        text(String.format("Score:\n%d ", ng.currentValue), width / 2.0f + width / 4.0f, height / 2.0f - height / 8.0f);
        text(String.format("Goal:\n%d ", 2048), width / 4.0f, height / 2.0f - height / 8.0f);
        text(String.format("Best:\n%d ", bestResult), width / 2.0f + width / 4.0f, height / (3 / 2.0f));
        text("Control: Arrows. Change Goal: +, -", width / 2.0f, height - height / 10.0f);

    }

    public void keyPressed() {
        start = true;
        boolean isChanged = false;
        if (key == CODED) {
            switch (keyCode) {
                case UP:
                    isChanged = ng.moveUp();
                    break;
                case DOWN:
                    isChanged = ng.moveDown();
                    break;
                case LEFT:
                    isChanged = ng.moveLeft();
                    break;
                case RIGHT:
                    isChanged = ng.moveRight();
                    break;
            }
            int[][] temp = new int[4][4];
            copyArray(temp);
            if (!isChanged) {
                ng.addNum();
            } else {
                if (ng.isLost()) {
                    isLost = true;
                }
            }
            redraw();
        }
    }


    private void copyArray(int[][] temp) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ng.isUded[i][j] = false;
                temp[i][j] = ng.area[i][j];
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main(Task03.class);
    }
}