package io.github.studio22.lama;

import android.util.Log;

import java.util.Arrays;

/**
 * Математические операции с матрицей
 */
public class MatrixCalculation {
    /**
     * @param matrix матрица типа Double[][]
     * @return ранг матрицы
     */
    public static double[][] system(double[][] matrix){
        double[][] newMatrix = new double[matrix.length][matrix[0].length];
        double[][] temp = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++)
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[0].length);

        //Прямой ход
        for (int k = 0; k < matrix.length; k++) //k-номер строки
        {
            for (int i = 0; i < matrix[0].length; i++) //i-номер столбца
                newMatrix[k][i] = newMatrix[k][i] / matrix[k][k]; //Деление k-строки на первый член !=0 для преобразования его в единицу
            for (int i = k + 1; i < matrix.length; i++) //i-номер следующей строки после k
            {
                double K = newMatrix[i][k] / newMatrix[k][k]; //Коэффициент
                for (int j = 0; j < matrix[0].length; j++) //j-номер столбца следующей строки после k
                    newMatrix[i][j] = newMatrix[i][j] - newMatrix[k][j] * K; //Зануление элементов матрицы ниже первого члена, преобразованного в единицу
            }
            for (int i = 0; i < matrix.length; i++) //Обновление, внесение изменений в начальную матрицу
                System.arraycopy(newMatrix[i], 0, temp[i], 0, temp.length);
        }

        //Обратный ход
        for (int k = temp.length - 1; k > -1; k--) //k-номер строки
        {
            for (int i = temp.length; i > -1; i--) //i-номер столбца
                newMatrix[k][i] = newMatrix[k][i] / temp[k][k];
            for (int i = k - 1; i > -1; i--) //i-номер следующей строки после k
            {
                double K = newMatrix[i][k] / newMatrix[k][k];
                for (int j = temp.length; j > -1; j--) //j-номер столбца следующей строки после k
                    newMatrix[i][j] = newMatrix[i][j] - newMatrix[k][j] * K;
            }
        }

        //Отделяем ответы
        double[][] answer = new double[1][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            answer[0][i] = newMatrix[i][matrix.length];
        }

        return answer;
    }

    /**
     * @param matrix матрица типа Double[][]
     * @return ранг матрицы
     */
    public static double[][] rang(double[][] matrix){
        double[][] result = new double[matrix.length][matrix[0].length + 1];
        double[][] rang = new double[1][1];

        for (int i = 0; i < matrix.length; i++)
            System.arraycopy(matrix[i], 0, result[i], 0, matrix[0].length);

        //Прямой обход
        for (int k = 0; k < matrix.length; k++) //k-номер строки
        {
            for (int i = k + 1; i < matrix.length; i++) //i-номер следующей строки после k
            {
                if (result[k][k] != 0) {
                    double K = result[i][k] / result[k][k]; //Коэффициент
                    for (int j = 0; j < matrix[0].length; j++) //j-номер столбца следующей строки после k
                        result[i][j] = result[i][j] - result[k][j] * K; //Зануление элементов матрицы ниже первого члена, преобразованного в единицу
                } else {
                    rang[0][0] = 0;
                    return rang;
                }
            }
        }

        rang[0][0] = result.length;
        for (double[] doubles : result) {
            for (int j = 0; j < result[0].length - 1; j++) {
                if (doubles[j] != 0) {
                    break;
                }
                if (j == result[0].length - 2) {
                    rang[0][0]--;
                }
            }
        }

        return rang;
    }

    /**
     * @param matrix матрица типа Double[][]
     * @return приведенная к треугольному виду матрицы
     */
    public static double[][] triangleView(double[][] matrix){
        double[][] result = new double[matrix.length][matrix[0].length + 1];

        for (int i = 0; i < matrix.length; i++)
            System.arraycopy(matrix[i], 0, result[i], 0, matrix[0].length);

        //Прямой обход
        for (int k = 0; k < matrix.length; k++) //k-номер строки
        {
            for (int i = k + 1; i < matrix.length; i++) //i-номер следующей строки после k
            {
                if (result[k][k] != 0) {
                    double K = result[i][k] / result[k][k]; //Коэффициент
                    for (int j = 0; j < matrix[0].length; j++) //j-номер столбца следующей строки после k
                        result[i][j] = result[i][j] - result[k][j] * K; //Зануление элементов матрицы ниже первого члена, преобразованного в единицу
                }
            }
        }

        return result;
    }

    /**
     * @param matrix матрица типа Double[][]
     * @return транспонированная матрица типа String
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
    public static double determinantCalc(double[][] matrix) {
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
        Log.d("result", Arrays.deepToString(result));
        Log.d("Size", String.valueOf(result.length) + result[0].length);
        return result;
    }

    /**
     * @param matrix матрица
     * @param rowN номер строки
     * @param columnN номер столбца
     * @return часть матрицы
     */
    private static double[][] extractMatrix(double[][] matrix, int rowN, int columnN) {
        double[][] result = new double[rowN][columnN];
        for (int i = 0; i < rowN; i++) {
            if (columnN >= 0) System.arraycopy(matrix[i], 0, result[i], 0, columnN);
        }

        return result;
    }

    /**
     * @param matrix матрица
     * @return критерий Сильвестра
     */
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

    /**
     * Поиск собственных значений
     * @param matrix матрица
     * @return собственные значения
     */
    public static double[][] eigenValue(double[][] matrix) {
        if (matrix.length == 2 & matrix[0].length == 2) {
            //TODO выяснить, почему ответы получаются с обратным знаком
            double D = Math.pow(matrix[0][0] + matrix[1][1], 2) -
                    4 * (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
            if (D > 0) {
                double lambda1 = (-(matrix[0][0] + matrix[1][1]) + Math.sqrt(D)) / 2;
                double lambda2 = (-(matrix[0][0] + matrix[1][1]) - Math.sqrt(D)) / 2;
                double[][] result = new double[2][1];
                // добавяем минусы, чтобы получить правильные ответы
                result[0][0] = -lambda1;
                result[1][0] = -lambda2;
                return result;
            } else {
                return matrix;
            }
        } else {
            return matrix;
        }
    }

    /**
     * Поиск собственных векторов
     * @param matrix матрица
     * @return собственые вектора
     */
    public static double[][] eigenVector(double[][] matrix) {
        if (matrix.length == 2 & matrix[0].length == 2) {
            //TODO выяснить, почему ответы получаются с обратным знаком
            double[][] result = new double[2][1];
            double[][] eigenValue = eigenValue(matrix);
            double lambda1 = eigenValue[0][0];
            double lambda2 = eigenValue[1][0];
            double vector1Rate = -(matrix[0][0] - lambda1) / matrix[1][0]; // коэффициент первого вектора x = k1*y
            double vector2Rate = -(matrix[0][0] - lambda2) / matrix[1][0]; // коэффициент второго вектора x = k2*y
            // добавяем минусы, чтобы получить правильные ответы
            result[0][0] = -vector1Rate;
            result[1][0] = -vector2Rate;
            return result;
        } else {
            return matrix;
        }
    }
}
