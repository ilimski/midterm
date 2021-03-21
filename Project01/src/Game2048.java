import java.util.ArrayList;

class Game2048 {
    int currentValue = 0;
    private ArrayList<int[][]> animations;

    Game2048(ArrayList<int[][]> animations) {
        this.animations = animations;
        addNum();
        addNum();
    }

    int[][] area = new int[4][4];
    boolean[][] isUded = new boolean[4][4];

    boolean isLost() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (area[i][j] == 0) {
                    return false;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (area[i][j] == area[i][j + 1]) {
                    return false;
                }
            }
        }
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                if (area[i][j] == area[i + 1][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    void addNum() {
        int emptySpot = 0;
        boolean isEmpty = false;
        for (int[] ints : area) {
            for (int j = 0; j < area.length; j++) {
                if (ints[j] == 0) {
                    emptySpot++;
                    isEmpty = true;
                }
            }
        }
        if (!isEmpty) {
            return;
        }
        int random = (int) (Math.random() * emptySpot) + 1;
        int counter = 0;
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area.length; j++) {
                if (area[i][j] == 0) {
                    counter++;
                    if (counter == random) {
                        area[i][j] = randomNum();
                        break;
                    }
                }
            }
        }
    }

    private int randomNum() {
        return (int) (Math.random() * 10) + 1 == 1 ? 4 : 2;
    }

    boolean moveDown() {
        boolean isSame = true, isMove = false;
        for (int k = 0; k < 4; k++) {
            for (int j = 0; j < area.length; j++) {
                for (int i = area.length - 1; i > 0; i--) {
                    if (area[i][j] == area[i - 1][j] && area[i][j] != 0 && !isUded[i][j]  && !isUded[i-1][j]) {
                        area[i - 1][j] = 0;
                        currentValue += area[i][j] *= 2;
                        isUded[i][j] = true;
                        isSame = false;
                    } else if (area[i][j] == 0 && area[i - 1][j] != 0) {
                        area[i][j] = area[i - 1][j];
                        area[i - 1][j] = 0;
                        isSame = false;
                        isMove = true;
                    }
                }
            }
            if (isMove) {
                int[][] temp = new int[4][4];
                copyArray(temp);
                animations.add(temp);
            }
        }
        return isSame;
    }

    boolean moveRight() {
        boolean isSame = true, isMove = false;
        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 3; j > 0; j--) {
                    if (area[i][j] == area[i][j - 1] && area[i][j] != 0 && !isUded[i][j] && !isUded[i][j-1]) {
                        currentValue += area[i][j] *= 2;
                        area[i][j - 1] = 0;
                        isUded[i][j] = true;
                        isSame = false;
                    } else if (area[i][j] == 0 && area[i][j - 1] != 0) {
                        area[i][j] = area[i][j - 1];
                        area[i][j - 1] = 0;
                        isSame = false;
                        isMove = true;
                    }
                }
            }
            if (isMove) {
                int[][] temp = new int[4][4];
                copyArray(temp);
                animations.add(temp);
            }
        }
        return isSame;
    }

    boolean moveUp() {
        boolean isSame = true, isMove = false;
        for (int k = 0; k < 4; k++) {
            for (int j = 0; j < area.length; j++) {
                for (int i = 0; i < area.length - 1; i++) {
                    if (area[i][j] == area[i + 1][j] && area[i][j] != 0 && !isUded[i][j] && !isUded[i+1][j]) {
                        area[i + 1][j] = 0;
                        currentValue += area[i][j] *= 2;
                        isUded[i][j] = true;
                        isSame = false;
                    }
                    if (area[i][j] == 0 && area[i + 1][j] != 0) {
                        area[i][j] = area[i + 1][j];
                        area[i + 1][j] = 0;
                        isMove = true;
                        isSame = false;
                    }
                }
            }
            if (isMove) {
                int[][] temp = new int[4][4];
                copyArray(temp);
                animations.add(temp);
            }
        }
        return isSame;
    }

    boolean moveLeft() {
        boolean isSame = true, isMove = false;
        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < area.length; i++) {
                for (int j = 0; j < area.length - 1; j++) {
                    if (area[i][j] == area[i][j + 1] && area[i][j] != 0 && !isUded[i][j] && !isUded[i][j + 1]) {
                        area[i][j + 1] = 0;
                        currentValue += area[i][j] *= 2;
                        isUded[i][j] = true;
                        isSame = false;
                    }
                    if (area[i][j] == 0 && area[i][j + 1] != 0) {
                        area[i][j] = area[i][j + 1];
                        area[i][j + 1] = 0;
                        isSame = false;
                        isMove = true;
                    }
                }
            }
            if (isMove) {
                int[][] temp = new int[4][4];
                copyArray(temp);
                animations.add(temp);
            }
        }
        return isSame;
    }

    private void copyArray(int[][] temp) {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(area[i], 0, temp[i], 0, 4);
        }
    }
    void fillArray() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                isUded[i][j] = false;
            }
        }
    }
}