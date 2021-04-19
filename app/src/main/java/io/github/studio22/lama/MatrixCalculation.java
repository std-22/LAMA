package io.github.studio22.lama;

/**
 * Класс для совершения математических операция с матрицами
 */
public class MatrixCalculation {
    /**
     * @param matrix матрица типа Double[][]
     * @return транспонировання матрица типа String
     */
    public static double[][] transpose(double[][] matrix) {
        double[][] result = new double[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[j][i] = matrix[i][j];
            }
        }

        return result;
    }
    /**
     * @param matrix матрица типа Double[][]
     * @return обратная матрица
     */
    public static double[][] inverse(double[][] matrix) {
        double[][] inversed = new double[matrix.length][matrix[0].length];
        return inversed;
    }
    private static double determinantCalc(double[][] matrix) {
        if (matrix.length == 1)
            return matrix[0][0];
        if (matrix.length == 2)
            return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
        int sum = 0, sign = 1;
        int newN = matrix.length - 1;
        double[][] temp = new double[newN][newN];
        for (int t = 0; t < newN; t++) {
            int q = 0;
            for (int i = 0; i < newN; i++) {
                System.arraycopy(matrix[1 + i], q, temp[i], 0, newN);
                if (q == i)
                    q = 1;
            }
            sum += sign * matrix[0][t] * determinantCalc(temp);
            sign *= -1;
        }
        return sum;
    }

    public static double[][] determinant(double[][] matrix) {
        double[][] result = new double[1][1];
        result[0][0] = determinantCalc(matrix);
        return result;
    }
    public static double[][] criterionSilvester(double[][] matrix) {
        boolean firstMinor, secondMinor, thirdMinor;
        double[][] result = new double[1][1];
        firstMinor = matrix[0][0] > 0;
        secondMinor = (matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0]) > 0;
        thirdMinor = (matrix[0][0]*(matrix[1][1]*matrix[2][2]-matrix[1][2]*matrix[2][1]) -
                      matrix[0][1]*(matrix[1][0]*matrix[2][2]-matrix[1][2]*matrix[2][0]) +
                      matrix[0][2]*(matrix[1][0]*matrix[2][1]-matrix[1][1]*matrix[2][0])) > 0;
        if (firstMinor && secondMinor && thirdMinor) {
            result[0][0] = 1;
        } else if (!firstMinor && secondMinor && !thirdMinor) {
            result[0][0] = -1;
        } else {
            result[0][0] = 0;
        }
        return result;
    }
}
