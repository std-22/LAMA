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
        double[][] inverse = new double[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                inverse[i][j] = Math.pow(-1, i + j) * determinantCalc(minor(matrix, i, j));
            }
        }

        double det = 1.0 / determinantCalc(matrix);
        for (int i = 0; i < inverse.length; i++) {
            for (int j = 0; j <= i; j++) {
                double temp = inverse[i][j];
                inverse[i][j] = inverse[j][i] * det;
                inverse[j][i] = temp * det;
            }
        }
        return inverse;
    }

    /**
     * @param matrix матрица
     * @param row    номер ряда
     * @param column номер столбца
     * @return минор
     */
    private static double[][] minor(double[][] matrix, int row, int column) {
        double[][] minor = new double[matrix.length - 1][matrix.length - 1];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length & i != row; j++) {
                if (j != column) {
                    minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
                }
            }
        }

        return minor;
    }

    /**
     * @param matrix матрица
     * @return определитель матрцы типа double
     */
    private static double determinantCalc(double[][] matrix) {
        if (matrix.length == 1)
            return matrix[0][0];
        if (matrix.length == 2)
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        double det = 0;
        for (int i = 0; i < matrix[0].length; i++)
            det += Math.pow(-1, i) * matrix[0][i] * determinantCalc(minor(matrix, 0, i));
        return det;
    }

    /**
     * @param matrix матрица
     * @return определитель матрицы типа double[][]
     */
    public static double[][] determinant(double[][] matrix) {
        double[][] result = new double[1][1];
        result[0][0] = determinantCalc(matrix);
        return result;
    }

    private static double[][] extractMatrix(double[][] matrix, int rowN, int columnN) {
        double[][] result = new double[rowN][columnN];
        for (int i = 0; i < rowN; i++) {
            if (columnN >= 0) System.arraycopy(matrix[i], 0, result[i], 0, columnN);
        }

        return result;
    }

    public static double[][] criterionSilvester(double[][] matrix) {
        boolean positive = true;
        boolean negative = true;
        double[] minors = new double[matrix.length];
        double[][] result = new double[1][1];

        for (int i = 0; i < matrix.length; i++) {
            minors[i] = determinantCalc(extractMatrix(matrix, i + 1, i + 1));
        }

        for (double minor : minors) {
            if (minor < 0) {
                positive = false;
                break;
            }
        }

        for (int i = 0; i < minors.length / 2; i++) {
            if (minors[i] % 2 == 0 & minors[i] < 0 & minors[i + 1] % 2 == 1 & minors[i + 1] > 0) {
                negative = false;
                break;
            }
        }

        if (positive) {
            result[0][0] = 1;
        } else if (negative) {
            result[0][0] = -1;
        } else {
            result[0][0] = 0;
        }

        return result;
    }
}
