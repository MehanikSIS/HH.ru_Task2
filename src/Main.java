import java.util.*;

public class Main {

    public static int exist1(int y, int x, int[][] matrix) {
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

    public static boolean outBounds(int y, int x, int[][] matrix) {
        return x < 0 || y < 0 || x >= matrix[0].length || y >= matrix.length;
    }

    public static int square(int[][] matrix) {
        return (matrix.length) * matrix[0].length;
    }

    public static double coefficient(int[][] matrix) {
        return (double) fertile(matrix) / square(matrix);
    }

    public static int fertile(int[][] matrix) {
        int count = 0;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public static List<int[][]> listMatrix(int[][] matrix) {
        boolean[][] revealed = new boolean[matrix.length][matrix[0].length];
        List<int[][]> list = new ArrayList<>();
        List<Integer> yTemp = new ArrayList<>();
        List<Integer> xTemp = new ArrayList<>();
        int lastY = 0;
        int lastX = 0;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (revealed[y][x]) {
                    continue;
                }
                lastY = y;
                lastX = x;
                revealed[y][x] = true;
                if (matrix[y][x] == 1) {
                    yTemp.add(y);
                    xTemp.add(x);
                } else {
                    break;
                }
            }
        }
     /*   List<Integer> yFalse = new ArrayList<>();
        List<Integer> xFalse = new ArrayList<>();*/


        /* if (exist1(y, x, matrix) > 1) {*/

        Collections.sort(xTemp);
        Collections.sort(yTemp);
        int[][] result = new int[yTemp.get(yTemp.size() - 1) + 1][xTemp.get(xTemp.size() - 1) + 1];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = matrix[i][j];
            }
        }

        if (fertile(result) > 1)
            list.add(result);
        /*for (int y = 0; y < revealed.length; y++) {
            for (int x = 0; x < revealed[0].length; x++) {
                if (!revealed[y][x]) {
                    yFalse.add(y);
                    xFalse.add(x);
                }
            }
        }
       List<Integer> sizeY = new ArrayList<>(yFalse);
        List<Integer> sizeX = new ArrayList<>(xFalse);
        Collections.sort(sizeY);
        Collections.sort(sizeX);*/
        int[][] newMatrix;
        System.arraycopy(matrix,lastY,);
        int count = 0;
        for (int y = 0; y < newMatrix.length; y++) {
            for (int x = 0; x < newMatrix[0].length; x++) {
                newMatrix[y][x] = matrix[yFalse.get(y)][xFalse.get(x)];
                count++;
            }
        }
        if (count > 1) {
            listMatrix(newMatrix);
        }
        return list;
    }

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


        double k = 0;
        List<int[][]> list = listMatrix(matrix);
        int[][] result = list.get(0);
        for (int[][] mat : list) {
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
