import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String str = in.nextLine();

        int n = Integer.parseInt(str.split(" ")[0]);
        int m = Integer.parseInt(str.split(" ")[1]);

        int[][] matrix = new int[m][n];
        String tempString;
        for (int y = 0; y < matrix.length; y++) {
            tempString = in.nextLine();
            for (int x = 0; x < matrix[y].length; x++) {
                matrix[y][x] = Integer.parseInt(tempString.split(" ")[x]);
            }
        }
        System.out.println(Arrays.deepToString(matrix));
    }
}