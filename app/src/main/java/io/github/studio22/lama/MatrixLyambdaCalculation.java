package io.github.studio22.lama;

/**
 * Класс для совершения операций над матрцей и числом
 */
public class MatrixLyambdaCalculation {
    /**
     * @param matrix матрица
     * @param number число
     * @return  результат поэлементого суммирования числа и матрицы
     */
    public static String addNumber(Double[][] matrix, Double number) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result.append(matrix[i][j] + number);
                result.append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }

    /**
     * @param matrix матрица
     * @param number число
     * @return результат поэлементого вычитания числа из матрицы
     */
    public static String subtractNumber(Double[][] matrix, Double number) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result.append(matrix[i][j] - number);
                result.append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
    /**
     * @param matrix матрица
     * @param number число
     * @return результат поэлементого умножения матрицы на число
     */
    public static String multiplyOnNumber(Double[][] matrix, Double number) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result.append(matrix[i][j] * number);
                result.append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
    /**
     * @param matrix матрица
     * @param number число
     * @return результат поэлементого деления матрицы на число
     */
    public static String divideOnNumber(Double[][] matrix, Double number) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result.append(matrix[i][j] / number);
                result.append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
    /**
     * @param matrix матрица
     * @param number число
     * @return результат поэлементого возведения элементов матрицы в число number
     */
    public static String powerElemByNumber(Double[][] matrix, Double number) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result.append(Math.pow(matrix[i][j], number));
                result.append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
    /**
     * @param matrix матрица
     * @param number число
     * @return результат поэлементого возведения матрицы в степень числа number
     */
    public static String powerMatrixByNumber(Double[][] matrix, Integer number) {
        StringBuilder result = new StringBuilder();
        for (int n = 0; n < number; n++) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j] *= matrix[i][j];
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result.append(matrix[i][j]);
                result.append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
}
