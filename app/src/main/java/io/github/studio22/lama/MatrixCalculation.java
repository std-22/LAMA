package io.github.studio22.lama;

/**
 * Класс для совершения математических операция с матрицами
 */
public class MatrixCalculation {
    /**
     * @param matrix матрица типа Double[][]
     * @return определитель матрицы
     */
    public static Double matrixDeterminant (Double[][] matrix) {
        Double[][] temporary;
        Double result = 0.0;

        if (matrix.length == 1) {
            result = matrix[0][0];
            return (result);
        }

        if (matrix.length == 2) {
            result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
            return (result);
        }

        for (int i = 0; i < matrix[0].length; i++) {
            temporary = new Double[matrix.length - 1][matrix[0].length - 1];

            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    if (k < i) {
                        temporary[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        temporary[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            result += matrix[0][i] * Math.pow (-1, (double) i) * matrixDeterminant (temporary);
        }
        return result;
    }

    /**
     * @param matrix матрица типа Double[][]
     * @return транспонировання матрица типа String
     */
    public static String transpose(Double[][] matrix) {
        StringBuilder transposed = new StringBuilder();
        double[][] newField = new double[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                newField[j][i] = matrix[i][j];
            }
        }

        for (double[] ints : newField) {
            for (int j = 0; j < newField[0].length; j++) {
                transposed.append(ints[j]);
                transposed.append(" ");
            }
            transposed.append("\n");
        }

        return transposed.toString();
    }
}
