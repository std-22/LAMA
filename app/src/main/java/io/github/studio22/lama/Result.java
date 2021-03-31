package io.github.studio22.lama;

/**
 * Класс получения результата вычислений
 */
public class Result {
    private static String result = "";
    private static String result_1 = "";

    /**
     * @param nameOfFunction название функции
     * @param field матрица
     * @return результат вычислления
     */
    public static String getResult(String nameOfFunction, Double[][] field){
        switch (nameOfFunction){
            case "Критерий Сильвестра":
                boolean firstMinor, secondMinor, thirdMinor;
                firstMinor = field[0][0] > 0;
                secondMinor = (field[0][0]*field[1][1] - field[0][1]*field[1][0]) > 0;
                thirdMinor = (field[0][0]*(field[1][1]*field[2][2]-field[1][2]*field[2][1]) -
                              field[0][1]*(field[1][0]*field[2][2]-field[1][2]*field[2][0]) +
                              field[0][2]*(field[1][0]*field[2][1]-field[1][1]*field[2][0])) > 0;
                if (firstMinor && secondMinor && thirdMinor) {
                    result = "Positive definite";
                } else if (!firstMinor && secondMinor && !thirdMinor) {
                    result = "Negative definite";
                } else {
                    result = "Alternating definite";
                }
                result_1 = result;
                result = "";
                return result_1;
            case "Транспонирование":
                return MatrixCalculation.transpose(field);
            case "DET |A|":
                return String.valueOf(MatrixCalculation.matrixDeterminant(field));
            default:
                return "Something get wrong";
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
                return MatrixLyambdaCalculation.addNumber(matrix, number);
            case "Поэлементное A - n":
                return MatrixLyambdaCalculation.subtractNumber(matrix, number);
            case "Поэлементное A \u00D7 n":
                return MatrixLyambdaCalculation.multiplyOnNumber(matrix, number);
            case "Поэлементное A / n":
                return MatrixLyambdaCalculation.divideOnNumber(matrix, number);
            case "Поэлементное A\u207F":
                return MatrixLyambdaCalculation.powerElemByNumber(matrix, number);
            case "A\u207F":
                return MatrixLyambdaCalculation.powerMatrixByNumber(matrix, number.intValue());
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
    public static String getResult(String nameOfFunction, Double[][] matrixA, Double[][] matrixB) {
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
                return "Something get wrong";
        }
    }
}
