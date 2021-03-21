import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) throws IOException {
        FileInputStream read = new FileInputStream("C:\\Users\\admin\\Desktop\\программирование\\MiniCosmos.txt");
        int k;
        String s = "";
        while ((k = read.read()) != -1) {
            s += (char) k;
        }
        System.out.println(s);
        ArrayList<char[][]> list = new ArrayList<>();
        String[] strs = s.split("\\n");
        int counter = 0;
        for (int l = 0; l < strs.length; l++) {
            for (int j = 0; j < strs[l].length(); j++) {
                if (strs[l].charAt(j) == ';') {
                  char[][] map = new char[counter][];
                    for (int t = 0; t < map.length; t++) {
                        map[t] = new char[strs[l - counter].length()];
                        for (int y = 0; y < map[t].length; y++) {
                            map[t][y] = strs[l - counter].charAt(y);
                        }
                        counter--;
                    }
                    counter = 0;
                    list.add(map);
                    l += 2;
                    break;
                }
            }
            counter++;
        }
        for
        (int r =0 ; r < list.size();r++){
        char[][] temp = list.get(r);
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                System.out.print(temp[i][j]);
            }
            System.out.println();
        }
            System.out.println();
        }
    }
}
