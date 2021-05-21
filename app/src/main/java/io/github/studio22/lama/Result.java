package io.github.studio22.lama;

/**
 * Класс получения результата вычислений
 */
public class Result {
    /**
     * @param nameOfFunction название функции
     * @param matrix матрица
     * @return результат вычислления
     */
    public static double[][] getResult(String nameOfFunction, double[][] matrix){
        switch (nameOfFunction){
            case "Критерий Сильвестра":
                return MatrixCalculation.criterionSilvester(matrix);
            case "Транспонирование":
                return MatrixCalculation.transpose(matrix);
            case "DET |A|":
                return MatrixCalculation.determinant(matrix);
            case "A\u1428\u00B9":
                return MatrixCalculation.inverse(matrix);
            default:
                return matrix;
        }
    }

    /**
     * @param nameOfFunction название функции
     * @param matrix матрица
     * @param number число
     * @return результат вычисления
     */
    public static String getResult(String nameOfFunction, Double[][] matrix, Double number) {
        switch (nameOfFunction) {
            case "Поэлементное A + n":
                return MatrixLambdaCalculation.addNumber(matrix, number);
            case "Поэлементное A - n":
                return MatrixLambdaCalculation.subtractNumber(matrix, number);
            case "Поэлементное A \u00D7 n":
                return MatrixLambdaCalculation.multiplyOnNumber(matrix, number);
            case "Поэлементное A / n":
                return MatrixLambdaCalculation.divideOnNumber(matrix, number);
            case "Поэлементное A\u207F":
                return MatrixLambdaCalculation.powerElemByNumber(matrix, number);
            case "A\u207F":
                return MatrixLambdaCalculation.powerMatrixByNumber(matrix, number.intValue());
            default:
                return "Something get wrong";
        }
    }

    /**
     * @param nameOfFunction название функции
     * @param matrixA первая матрица
     * @param matrixB вторая матрица
     * @return результат вычисления
     */
    public static double[][] getResult(String nameOfFunction, double[][] matrixA, double[][] matrixB) {
        switch (nameOfFunction) {
            case "A \u00D7 B":
                return MatrixMatrixCalculation.multiplyMatrices(matrixA, matrixB);
            case "A \u00D7 B\u207B\u00B9":
                return MatrixMatrixCalculation.multiplyMatrices(matrixA,
                        MatrixCalculation.inverse(matrixB));
            case "A + B":
                return MatrixMatrixCalculation.sum(matrixA, matrixB);
            case "A - B":
                return MatrixMatrixCalculation.subtract(matrixA, matrixB);
            case "Поэлементное A \u00D7 B":
                return MatrixMatrixCalculation.multiplyByElem(matrixA, matrixB);
            case "Поэлементное A / B":
                return MatrixMatrixCalculation.divideByElem(matrixA, matrixB);
            default:
                return matrixA;
        }
    }
}