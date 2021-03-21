package io.github.studio22.lama;

public class Result {
    private static String result = "";
    private static String result_1 = "";

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
                Double det = MatrixCalculation.matrixDeterminant(field);
                return String.valueOf(det);
            default:
                return "";
        }
    }
}
