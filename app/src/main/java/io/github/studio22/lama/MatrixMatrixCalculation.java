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
    public static String sum(Double[][] matrixA, Double[][] matrixB) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                result.append(matrixA[i][j] + matrixB[i][j]);
                result.append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
    /**
     * @param matrixA первая матрица
     * @param matrixB вторая матрица
     * @return результат вычитания матриц
     */
    public static String subtract(Double[][] matrixA, Double[][] matrixB) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                result.append(matrixA[i][j] - matrixB[i][j]);
                result.append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
    /**
     * @param matrixA первая матрица
     * @param matrixB вторая матрица
     * @return результат поэлементного умножения матриц
     */
    public static String multiplyByElem(Double[][] matrixA, Double[][] matrixB) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                result.append(matrixA[i][j] * matrixB[i][j]);
                result.append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
    /**
     * @param matrixA первая матрица
     * @param matrixB вторая матрица
     * @return результат поэлементного деления матриц
     */
    public static String divideByElem(Double[][] matrixA, Double[][] matrixB) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                result.append(matrixA[i][j] / matrixB[i][j]);
                result.append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }

    /**
     * Вспомогательный метод
     * @param matrixA первая матрица
     * @param matrixB вторая матрица
     * @param row номер ряда
     * @param col номер столбца
     * @return значение ячейки
     */
    private static Double multiplyMatricesCell(Double[][] matrixA, Double[][] matrixB,
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
    public static String multiplyMatrices(Double[][] matrixA, Double[][] matrixB) {
        Double[][] result = new Double[matrixA.length][matrixB[0].length];
        StringBuilder resultStr = new StringBuilder();

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(matrixA, matrixB, row, col);
            }
        }

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                resultStr.append(result[row][col]);
                resultStr.append(" ");
            }
            resultStr.append("\n");
        }

        return resultStr.toString();
    }
}
