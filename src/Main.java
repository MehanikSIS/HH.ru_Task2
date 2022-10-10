import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static int exist(int y, int x, byte[][] matrix) {
        if (outBounds(y, x, matrix)) {
            return -1;
        }
        int i = 0;
        for (int setY = -1; setY <= 1; setY++) {
            for (int setX = -1; setX <= 1; setX++) {
                if (outBounds(setY + y, setX + x, matrix)) continue;
                i += matrix[setY + y][setX + x];
            }
        }
        return i;
    }

    public static boolean outBounds(int y, int x, byte[][] matrix) {
        return x < 0 || y < 0 || x >= matrix[0].length || y >= matrix.length;
    }

    public static int square(byte[][] matrix) {
        return (matrix.length) * matrix[0].length;
    }

    public static double coefficient(byte[][] matrix) {
        return (double) fertile(matrix) / square(matrix);
    }

    public static int fertile(byte[][] matrix) {
        int count = 0;
        for (byte[] bytes : matrix) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (bytes[x] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public static List<byte[][]> listMatrix(byte[][] matrix) {
        List<byte[][]> list = new ArrayList<>();
        byte[][] cached = new byte[matrix.length][matrix[0].length];
        for (int y = 0; y < cached.length; y++) {
            for (int x = 0; x < cached[y].length; x++) {
                cached[y][x] = 0;
            }
        }
        boolean isCached = false;
        int beginX = 0;
        int finishX = 0;

        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 1 && cached[y][x] != 1) {
                    if (!isCached) {
                        isCached = true;
                        beginX = x;
                    }
                }
                if ((matrix[y][x] == 0 && isCached) || (x == matrix[y].length - 1 && isCached)) {
                    if (matrix[y][x] == 0 && isCached) finishX = x;
                    else finishX = x + 1;
                    for (int i = 0; i < matrix.length; i++) {
                        for (int k = beginX; k < finishX; k++) {
                            if (matrix[i][k] == 1) cached[i][k] = 1;
                        }
                        if (i == matrix.length - 1 || matrix[i + 1][beginX] == 0) {
                            isCached = false;
                            list.add(cached);
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String str = in.nextLine();

        int n = Integer.parseInt(str.split(" ")[0]);
        int m = Integer.parseInt(str.split(" ")[1]);

        byte[][] matrix = new byte[m][n];
        String tempString;
        for (int y = 0; y < matrix.length; y++) {
            tempString = in.nextLine();
            for (int x = 0; x < matrix[y].length; x++) {
                matrix[y][x] = Byte.parseByte(tempString.split(" ")[x]);
            }
        }


        double k = 0;
        List<byte[][]> list = listMatrix(matrix);
        byte[][] result = list.get(0);
        for (byte[][] mat : list) {
            if (k < coefficient(mat)) {
                result = mat;
                k = coefficient(mat);
            } else if (k == coefficient(mat)) {

                if (square(mat) > square(result)) {
                    result = mat;
                }
            }
        }

        System.out.println(square(result));
        in.close();
    }
}
