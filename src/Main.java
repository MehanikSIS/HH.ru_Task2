import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<byte[][]> list = new ArrayList<>();

    public static int exist1(int y, int x, byte[][] matrix) {
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

    public static double coefficient(int fertile, int square) {
        return (double) fertile / square;
    }

    public static int fertile(byte[][] matrix) {
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

    public static void listMatrix(byte[][] matrix) {


        byte[][] cached = new byte[matrix.length][matrix[0].length];
        for (int y = 0; y < cached.length; y++) {
            System.arraycopy(matrix[y], 0, cached[y], 0, cached[y].length);
        }
        // List<Integer> yTemp = new ArrayList<>();
        // List<Integer> xTemp = new ArrayList<>();

        //List<Node> node = new ArrayList<>();
        //Node startNode = null;
        // Node endNode = null;
        int startX = -1;
        int endX = 100;
        int startY = -1;
        int endY = 100;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 2) continue;
                if (matrix[y][x] == 1) {
                    //                    if (cached[y][x] == 1) {
                    //                      continue;
                    //                }
                    // cached[y][x] = 1;
                    //    if (exist1(y, x, matrix) > 1) {
                    cached[y][x] = 2;
                    if (startX == -1) {
                        startX = x;
                        endX = x;
                    } else if (startX > x) {
                        startX = x;
                    } else if (endX < x) {
                        endX = x;
                    }
                    if (startY == -1) {
                        startY = y;
                        endY = y;
                    } else if (startY > y) {
                        startY = y;
                    } else if (endY < y) {
                        endY = y;
                    }
                } else {
                    if (!outBounds(y, x + 1, matrix) && (!outBounds(y + 1, x, matrix))) {
                        if (matrix[y][x] == 0 && matrix[y][x + 1] == 1 && matrix[y + 1][x] == 1) {
                            // if (startNode == null) startNode = new Node(y, x);
                            // else endNode = new Node(y, x);
                            if (startX == -1) {
                                startX = x;
                                endX = x;
                            } else if (startX > x) {
                                startX = x;
                            } else if (endX < x) {
                                endX = x;
                            }
                            if (startY == -1) {
                                startY = y;
                                endY = y;
                            } else if (startY > y) {
                                startY = y;
                            } else if (endY < y) {
                                endY = y;
                            }
                            cached[y][x] = 2;
                        } else {
                            cached[y][x] = 2;
                            break;
                        }
                    } else if (y == matrix.length - 1 && !outBounds(y, x + 1, matrix) && matrix[y][x] == 0) {
                        cached[y][x] = 2;
                        break;
                    }
                }
                //            if (!outBounds(y, x + 1, matrix)) {
                //               if (matrix[y][x + 1] ==0) {
                //                  break;
                //               }
                //           }
            }
        }


        // Collections.sort(xTemp);
        // Collections.sort(yTemp);
        int sizeY;
        int sizeX;
        // if (node.size() > 0) {
        //     for (Node node1 : node) {
        //         if (sizeY < node1.y)
        //             sizeY = node1.y;
        //         if (sizeX < node1.x)
        //             sizeX = node1.x;
        //     }
        if ((endX - startX) + 1 > 0 && endY - startY > 0) {
            sizeY = (endY - startY) + 1;
            sizeX = (endX - startX) + 1;

            byte[][] result = new byte[sizeY][sizeX];
            for (int y = 0; y < result.length; y++) {
                for (int x = 0; x < result[y].length; x++) {
                    result[y][x] = matrix[startY + y][startX + x];
                }
            }
            list.add(result);
            matrix = cached;
            if (fertile(matrix) > 1) {
                listMatrix(matrix);
            }
        }
    }

    /* public static void listMatrix2(byte[][] matrix) {
         byte[][] cached = new byte[matrix.length][matrix[0].length];
         for (int y = 0; y < cached.length; y++) {
             System.arraycopy(matrix[y], 0, cached[y], 0, cached[y].length);
         }
         // List<Integer> yTemp = new ArrayList<>();
         // List<Integer> xTemp = new ArrayList<>();

         List<Node> node = new ArrayList<>();
         for (int y = 0; y < matrix.length; y++) {
             for (int x = 0; x < matrix[y].length; x++) {
                 if (matrix[y][x] == 2) continue;
                 if (matrix[y][x] == 1) {
                     //                    if (cached[y][x] == 1) {
                     //                      continue;
                     //                }
                     // cached[y][x] = 1;
                     //    if (exist1(y, x, matrix) > 1) {
                     node.add(new Node(y, x));
                     cached[y][x] = 2;
                 } else {
                     if (!outBounds(y, x + 1, matrix) && (!outBounds(y + 1, x, matrix))) {
                         if (matrix[y][x] == 0 && matrix[y][x + 1] == 1 && matrix[y + 1][x] == 1) {
                             node.add(new Node(y, x));
                             cached[y][x] = 2;
                         } else {
                             cached[y][x] = 2;
                             break;
                         }
                     } else if (y == matrix.length - 1 && !outBounds(y, x + 1, matrix) && matrix[y][x] == 0) {
                         cached[y][x] = 2;
                         break;
                     }
                 }
                 //            if (!outBounds(y, x + 1, matrix)) {
                 //               if (matrix[y][x + 1] ==0) {
                 //                  break;
                 //               }
                 //           }
             }
         }

         // Collections.sort(xTemp);
         // Collections.sort(yTemp);
         int sizeY = 0;
         int sizeX = 0;
         if (node.size() > 0) {
             for (Node node1 : node) {
                 if (sizeY < node1.y)
                     sizeY = node1.y;
                 if (sizeX < node1.x)
                     sizeX = node1.x;
             }

             byte[][] result = new byte[sizeY + 1][sizeX + 1];
             for (int i = 0; i < result.length; i++) {
                 System.arraycopy(matrix[i], 0, result[i], 0, result[i].length);
             }
             list.add(result);
             matrix = cached;
             if (fertile(matrix) > 1) {
                 listMatrix(matrix);
             }
         }
     }
 */
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
        //   byte[][] matrix2 = new byte[matrix.length][matrix[0].length];
        //  for (int y = 0; y < matrix2.length; y++) {
        //     System.arraycopy(matrix[y], 0, matrix2[y], 0, matrix2[y].length);
        // }
//        byte[][] cached = new byte[matrix.length][matrix[0].length];
//        for (byte[] bytes : cached) {
//            Arrays.fill(bytes, (byte) 0);
//        }
        listMatrix(matrix);
        //  listMatrix2(matrix2);
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


