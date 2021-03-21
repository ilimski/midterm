import processing.core.PApplet;

import java.io.*;
import java.util.ArrayList;

public class Main extends PApplet {
    private Animation animation = new Animation();
    private ArrayList<int[][]> animations = animation.animations;
    private Game2048 ng = new Game2048(animations);
    private boolean start = false, isLost = false, isWin = false;
    private int[] goals = {16, 32, 64, 128, 256, 512, 1024, 2048};
    private int[] bests = new int[8];
    private int goal = goals[0], index;
    private int size = 100, maxScore, animatedSquareSize = 15;


    public void settings() {
        fullScreen();
    }

    public void setup() {
        try {
            readBest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        frameRate(30);
        maxScore = bests[index];
        textSize(45);
        strokeWeight(10);
        stroke(242, 168, 7);
        background(0);
        textAlign(CENTER, CENTER);
        setBackground();
        drawGrid();
    }

    public void draw() {
        maxScore = bests[index];
        goal = goals[index];
        if (animations.size() - 1 > 0){
            drawMoveAnimation(animations.get(0));
            animations.remove(0);
        } else if (animations.size() == 1) {
            drawAnimation(getI(), getJ(), animations.get(0));
            if (animatedSquareSize >= 80) {
                animatedSquareSize = 15;
                animations.remove(animations.size() - 1);
            }
            animatedSquareSize += 20;
        } else {
            if (isLost) {
                try {
                    lost();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isWin) {
                try {
                    win();
                    writeBest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (start) {
                drawGrid();
            }
        }

    }

    private void win() {
        push();
        fill(0, 0, 220, 5);
        noStroke();
        rectMode(CENTER);
        rect(width / 2f, height / 2f, width / 1.5f, height / 4f);
        fill(242, 242, 44);
        text("Congratulations! Press Enter to continue ", width / 2f, height / 2f);
        pop();
    }


    private void readBest() throws IOException {
        FileInputStream read = new FileInputStream("C:\\Users\\admin\\Desktop\\программирование\\maxScore.txt");
        int i;
        StringBuilder s = new StringBuilder();
        while ((i = read.read()) != -1) {
            s.append((char) i);
        }
        String[] best = s.toString().split("\\n");
        for (int k = 0; k < best.length; k++) {
            if (!best[k].equals("")) {
                bests[k] = Integer.parseInt(best[k]);
            }
        }
    }

    private void lost() throws IOException {
        fill(0, 0, 220, 5);
        noStroke();
        rectMode(CENTER);
        rect(width / 2f, height / 2f, width / 1.5f, height / 4f);
        fill(242, 242, 44);
        text("You lost", width / 2f, height / 2f);
        writeBest();
    }

    private void writeBest() throws IOException {
        if (ng.currentValue > maxScore) {
            FileOutputStream write = new FileOutputStream("C:\\Users\\admin\\Desktop\\программирование\\maxScore.txt", false);
            bests[index] = ng.currentValue;
            for (int j = 0; j < 8; j++) {
                String s = String.format("%d\n", bests[j]);
                byte[] buffer = new byte[s.length()];
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = (byte) s.charAt(i);
                }
                write.write(buffer, 0, buffer.length);
            }
        }
    }


    private void drawMoveAnimation(int[][] array) {
        clearBackground();
        setBackground();
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                displayRect(i, j, 100, 100, 100);
                if (array[i - 1][j - 1] != 0) {
                    int exp = Math.getExponent(array[i - 1][j - 1] - 1);
                    displayRect(i, j, 20 * exp + 20 * exp, 0, 255 - 20 * exp);
                    displayText(i, j, array);
                }
            }
        }
    }

    private void drawAnimation(int i1, int j1, int[][] area) {
        clearBackground();
        setBackground();
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                displayRect(i, j, 100, 100, 100);
                if (area[i - 1][j - 1] != 0) {
                    int exp = Math.getExponent(area[i - 1][j - 1] - 1);
                    displayRect(i, j, 20 * exp + 20 * exp, 0, 255 - 20 * exp);
                    displayText(i, j, area);
                } else {
                    if (i - 1 == i1 && j - 1 == j1) {
                        fill(0, 0, 250);
                        push();
                        rectMode(CENTER);
                        noStroke();
                        rect(size * j + width / 4.0f + size / 2.0f, size * i + height / 8.0f + size / 2.0f, animatedSquareSize, animatedSquareSize, 10);
                        pop();
                    }

                }
            }
        }
    }

    private void drawGrid() {
        clearBackground();
        setBackground();
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                displayRect(i, j, 100, 100, 100);
                if (ng.area[i - 1][j - 1] != 0) {
                    int exp = Math.getExponent(ng.area[i - 1][j - 1] - 1);
                    displayRect(i, j, 20 * exp + 20 * exp, 0, 255 - 20 * exp);
                    displayText(i, j, ng.area);
                }
            }
        }
    }

    private void displayRect(int i, int j, int i2, int i3, int i4) {
        fill(i2, i3, i4);
        rect(size * j + width / 4.0f, size * i + height / 8.0f, size, size, 10);
    }

    private void displayText(int i, int j, int[][] area) {
        fill(252, 94, 3);
        text(area[i - 1][j - 1], size * j + width / 4.0f + size / 2.0f, size * i + height / 8.0f + size / 2.0f);
    }

    private void clearBackground() {
        fill(0);
        push();
        noStroke();
        rect(0, 0, width * 2, height * 2);
        pop();
    }

    private void setBackground() {
        textSize(70);
        fill(240, 46, 214);
        text("Game: 2048", width / 2.0f - size / 4.0f, height / 8.0f);
        fill(242, 242, 44);
        textSize(35);
        text(String.format("Score:\n%d ", ng.currentValue), width / 2.0f + width / 4.0f, height / 2.0f - height / 8.0f);
        text(String.format("Goal:\n%d ", goal), width / 4.0f, height / 2.0f - height / 8.0f);
        text(String.format("Best:\n%d ", maxScore), width / 2.0f + width / 4.0f, height / (3 / 2.0f));
        text("Control: Arrows. Change Goal: +, -", width / 2.0f, height - height / 10.0f);

    }

    public void keyPressed() {
        if (animations.size() == 0) {
            start = true;
            boolean isSame = false;
            if (key == CODED) {
                switch (keyCode) {
                    case UP:
                        isSame = ng.moveUp();
                        break;
                    case DOWN:
                        isSame = ng.moveDown();
                        break;
                    case LEFT:
                        isSame = ng.moveLeft();
                        break;
                    case RIGHT:
                        isSame = ng.moveRight();
                        break;
                }
                if (ng.currentValue > goal) {
                    index = goals.length - 1;
                    isWin = true;
                    start = false;
                }
                if (keyCode != SHIFT) {
                    int[][] temp = new int[4][4];
                    copyArray(temp);
                    ng.fillArray();
                    if (!isSame) {
                        animations.add(temp);
                        ng.addNum();
                    } else {
                        if (ng.isLost()) {
                            isLost = true;
                        }
                    }
                    redraw();
                }
            } else if (key == '+') {
                if (index < goals.length - 1) {
                    index++;
                    ng = new Game2048(animations);
                }
            } else if (key == '-') {
                if (index > 0) {
                    index--;
                    ng = new Game2048(animations);
                }
            } else if (key == ENTER && isWin) {
                isWin = false;
            }
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


    private int getI() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (ng.area[i][j] != animations.get(0)[i][j]) {
                    return i;
                }
            }
        }
        return 0;
    }

    private int getJ() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (ng.area[i][j] != animations.get(0)[i][j]) {
                    return j;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        PApplet.main(Main.class);
    }
}