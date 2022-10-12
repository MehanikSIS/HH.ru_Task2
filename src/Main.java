import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static byte[][] matrix;                                        // основное поле
    public static boolean[][] array;                                      // вспомогательный массив для нахождения прямоугольников
    public static List<byte[][]> list = new ArrayList<>();                // список прямоугольников на поле

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

    public static void existFound(Node node, List<Node> list) {            // поиск остальных плодородных участков после нахождения первого

        for (int setY = -1; setY <= 1; setY++) {
            for (int setX = -1; setX <= 1; setX++) {
                if (outBounds(setY + node.y, setX + node.x)) continue;
                if (matrix[node.y + setY][node.x + setX] == 1 && !array[node.y + setY][node.x + setX]) {
                    array[node.y + setY][node.x + setX] = true;
                    list.add(new Node(node.y + setY, node.x + setX));
                    existFound(list.get(list.size() - 1), list);
                }
            }
        }
    }

    public static boolean outBounds(int y, int x) {             // координаты вне массива
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
        byte[][] result;
        boolean flag = false;
        List<Node> nodeList = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 0) {
                    array[y][x] = true;
                    continue;
                }
                if (array[y][x]) {
                    continue;
                }
                if (matrix[y][x] == 1 && !array[y][x] && existNotFound(y, x)) {
                    nodeList.add(new Node(y, x));
                    array[y][x] = true;
                    flag = true;
                    break;
                } else {
                    array[y][x] = true;
                }
            }
            if (flag) {
                break;
            }
        }
        if (nodeList.size() > 0) {
            existFound(nodeList.get(0), nodeList);
            int minY = -1;
            int maxY = -1;
            int minX = -1;
            int maxX = -1;
            for (Node node : nodeList) {
                if (minY == -1 || minY > node.y) minY = node.y;
                if (minX == -1 || minX > node.x) minX = node.x;
                if (maxY == -1 || maxY < node.y) maxY = node.y;
                if (maxX == -1 || maxX < node.x) maxX = node.x;
            }
            result = new byte[maxY - minY + 1][maxX - minX + 1];
            for (int y = 0; y < result.length; y++) {
                System.arraycopy(matrix[minY + y], minX, result[y], 0, result[y].length);
            }
            list.add(result);
        }
        int count = 0;
        for (boolean[] booleans : array) {
            for (boolean aBoolean : booleans) {
                if (!aBoolean) {
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
        in.close();
        array = new boolean[m][n];
        listMatrix(matrix);
        if (list.isEmpty()) {
            System.out.println(0);
            return;
        }
        byte[][] result = list.get(0);
        double k = 0.0;
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