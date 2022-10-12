import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static byte[][] matrix;                                        // основное поле
    public static boolean[][] array;                                      // вспомогательный массив для нахождения прямоугольников
    public static List<byte[][]> list = new ArrayList<>();                   // список прямоугольников на поле

    public static boolean existNotFound(int y, int x) {                   // соприкосновения плодородных участков до нахождения вершины
        if (outBounds(y, x)) {
            return false;
        }
        int i = 0;
        for (int setY = -1; setY <= 1; setY++) {
            for (int setX = -1; setX <= 1; setX++) {
                if (outBounds(setY + y, setX + x)) continue;
                i += matrix[setY + y][setX + x];
            }
        }
        return i > 1;
    }

    public static boolean existFound(Node node, int y, int x) {            // соприкосновения плодородных участков после нахождения вершины
        boolean flag = false;
        for (int setY = -1; setY <= 1; setY++) {
            for (int setX = -1; setX <= 1; setX++) {
                if (outBounds(setY + y, setX + x)) continue;
                if (node.x == setX && node.y == setY) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public static boolean outBounds(int y, int x) {              // координаты вне массива
        return x < 0 || y < 0 || x >= matrix[0].length || y >= matrix.length;
    }

    public static int square(byte[][] matrix) {            // находим площадь прямоугольника
        return (matrix.length) * matrix[0].length;
    }

    public static double coefficient(int fertile, int square) {            // находим коэффициент плодородности прямоугольника
        return (double) fertile / square;
    }

    public static int fertile(byte[][] matrix) {                             // количество плодородных участков на прямоугольнике
        int count = 0;
        for (byte[] bytes : matrix) {
            for (byte abytes : bytes) {
                if (abytes == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void listMatrix(byte[][] matrix) {                            // нахождение и занесение в список прямоугольников на данном участке
        byte[][] result = null;
        Node firstNode = null;
        Node lastNode = null;
        boolean flag = false;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 1 && existNotFound(y, x) && !flag) {
                    array[y][x] = true;
                    firstNode = new Node(y, x);
                    lastNode = new Node(y, x);
                    flag = true;
                } else {
                    if (matrix[y][x] == 1 && (existFound(lastNode, y, x)) && flag) {
                        array[y][x] = true;
                        lastNode = new Node(y, x);
                    } else if (!outBounds(y, x + 1)) {
                        if (matrix[y][x + 1] == 1) {
                            array[y][x] = true;
                            lastNode = new Node(y, x);
                        }else{
                            array[y][x] = true;
                        }
                    } else {
                        array[y][x] = false;
                    }
                }
            }
        }
        if (firstNode != null && lastNode != null) {
            int sizeY = lastNode.y - firstNode.y + 1;
            int sizeX = lastNode.x - firstNode.x + 1;
            result = new byte[sizeY][sizeX];
            for (int y = 0; y < result.length; y++) {
                for (int x = 0; x < result[y].length; x++) {
                    result[y][x] = matrix[firstNode.y + y][firstNode.x + x];
                }
            }
        }
        list.add(result);
        int count = 0;
        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[y].length; x++) {
                if (!array[y][x]) {
                    count++;
                }
            }
        }
        if (count > 1) {
            listMatrix(matrix);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        int n = Integer.parseInt(str.split(" ")[0]);
        int m = Integer.parseInt(str.split(" ")[1]);
        matrix = new byte[m][n];
        String tempString;
        for (int y = 0; y < matrix.length; y++) {
            tempString = in.nextLine();
            for (int x = 0; x < matrix[y].length; x++) {
                matrix[y][x] = Byte.parseByte(tempString.split(" ")[x]);
            }
        }
        array = new boolean[m][n];
        listMatrix(matrix);
        byte[][] result = list.get(0);
        double k = 0;
        int s = 0;
        for (byte[][] bytes : list) {
            if ((k < coefficient(fertile(bytes), square(bytes))) && (square(bytes) > 1)) {
                result = bytes;
                k = coefficient(fertile(bytes), square(bytes));
                s = square(bytes);
            } else if ((k == coefficient(fertile(bytes), square(bytes)))) {
                if (s < square(bytes)) {
                    result = bytes;
                    s = square(bytes);
                }
            }
        }
        System.out.println(square(result));
        in.close();
    }

    static class Node {
        int x;
        int y;

        Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}


