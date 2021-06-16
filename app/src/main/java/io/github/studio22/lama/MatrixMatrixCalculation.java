package io.github.studio22.lama;

/**
 * Класс для совершения операция между матрицами
 */
public class MatrixMatrixCalculation {
    /**
     * @param matrixA первая матрица
     * @param matrixB вторая матрица
     * @return результат сложения матриц
     */
    public static double[][] sum(double[][] matrixA, double[][] matrixB) {
        double[][] result = new double[matrixA.length][matrixA[0].length];

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                result[i][j] = (matrixA[i][j] + matrixB[i][j]);
            }
        }
        return result;
    }
    /**
     * @param matrixA первая матрица
     * @param matrixB вторая матрица
     * @return результат вычитания матриц
     */
    public static double[][] subtract(double[][] matrixA, double[][] matrixB) {
        double[][] result = new double[matrixA.length][matrixA[0].length];

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                result[i][j] = (matrixA[i][j] - matrixB[i][j]);
            }
        }

        return result;
    }
    /**
     * @param matrixA первая матрица
     * @param matrixB вторая матрица
     * @return результат поэлементного умножения матриц
     */
    public static double[][] multiplyByElem(double[][] matrixA, double[][] matrixB) {
        double[][] result = new double[matrixA.length][matrixA[0].length];

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                result[i][j] = (matrixA[i][j] * matrixB[i][j]);
            }
        }

        return result;
    }
    /**
     * @param matrixA первая матрица
     * @param matrixB вторая матрица
     * @return результат поэлементного деления матриц
     */
    public static double[][] divideByElem(double[][] matrixA, double[][] matrixB) {
        double[][] result = new double[matrixA.length][matrixA[0].length];

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                result[i][j] = (matrixA[i][j] / matrixB[i][j]);
            }
        }

        return result;
    }
    /**
     * Вспомогательный метод
     * @param matrixA первая матрица
     * @param matrixB вторая матрица
     * @param row номер ряда
     * @param col номер столбца
     * @return значение ячейки
     */
    private static Double multiplyMatricesCell(double[][] matrixA, double[][] matrixB,
                                               int row, int col) {
        double cell = 0.0;
        for (int i = 0; i < matrixB.length; i++) {
            cell += matrixA[row][i] * matrixB[i][col];
        }
        return cell;
    }
    /**
     * @param matrixA первая матрица
     * @param matrixB вторая матрица
     * @return результат умножения первой матрицы на вторую
     */
    public static double[][] multiplyMatrices(double[][] matrixA, double[][] matrixB) {
        double[][] result = new double[matrixA.length][matrixB[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(matrixA, matrixB, row, col);
            }
        }

        return result;
    }
}