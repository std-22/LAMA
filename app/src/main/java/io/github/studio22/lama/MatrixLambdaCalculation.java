package io.github.studio22.lama;

/**
 * Класс для совершения операций над матрцей и числом
 */
public class MatrixLambdaCalculation {
    /**
     * @param matrix матрица
     * @param number число
     * @return результат поэлементого суммирования числа и матрицы
     */
    public static double[][] addNumber(double[][] matrix, Double number) {
        double[][] result = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j] + number;
            }
        }

        return result;
    }

    /**
     * @param matrix матрица
     * @param number число
     * @return результат поэлементого вычитания числа из матрицы
     */
    public static double[][] subtractNumber(double[][] matrix, Double number) {
        double[][] result = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j] - number;
            }
        }

        return result;
    }

    /**
     * @param matrix матрица
     * @param number число
     * @return результат поэлементого умножения матрицы на число
     */
    public static double[][] multiplyOnNumber(double[][] matrix, Double number) {
        double[][] result = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j] * number;
            }
        }

        return result;
    }

    /**
     * @param matrix матрица
     * @param number число
     * @return результат поэлементого деления матрицы на число
     */
    public static double[][] divideOnNumber(double[][] matrix, Double number) {
        double[][] result = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j] / number;
            }
        }

        return result;
    }

    /**
     * @param matrix матрица
     * @param number число
     * @return результат поэлементого возведения элементов матрицы в число number
     */
    public static double[][] powerElemByNumber(double[][] matrix, Double number) {
        double[][] result = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = Math.pow(matrix[i][j], number);
            }
        }

        return result;
    }

    /**
     * @param matrix матрица
     * @param lambda число
     * @return результат поэлементого возведения матрицы в степень числа lambda
     */
    public static double[][] powerMatrixByNumber(double[][] matrix, int lambda) {
        double[][] result = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, result[i], 0, matrix[0].length);
        }
        for (int n = 0; n < lambda - 1; n++) {
            double[][] temp = MatrixMatrixCalculation.multiplyMatrices(matrix, result);
            for (int i = 0; i < matrix.length; i++) {
                System.arraycopy(temp[i], 0, result[i], 0, matrix[0].length);
            }
        }

        return result;
    }
}
