package io.github.studio22.lama;

/**
 * Проверка корректности размерности введенной матрицы
 */
//TODO написать проверку ко всем операциям
public class CheckSize {
    public static boolean checkSize(String nameOfFunction,
                                    String selectedRowSize,
                                    String selectedColumnSize) {
        switch (nameOfFunction) {
            case "Критерий Сильвестра":
            case "Поиск собственных значений":
            case "Поиск собственных векторов":
            case "DET |A|":
            case "A\u1428\u00B9":
            case "A\u207F":
                return selectedRowSize.equals(selectedColumnSize);
            default:
                return true;
        }
    }

    public static boolean checkSize(String nameOfFunction,
                                    String selectedRowSizeA,
                                    String selectedColumnSizeA,
                                    String selectedRowSizeB,
                                    String selectedColumnSizeB) {
        switch (nameOfFunction) {
            case "A \u00D7 B":
            case "A \u00D7 B\u207B\u00B9":
                return selectedColumnSizeA.equals(selectedRowSizeB);
            case "A + B":
            case "A - B":
            case "Поэлементное A \u00D7 B":
            case "Поэлементное A / B":
                return selectedRowSizeA.equals(selectedRowSizeB) &&
                        selectedColumnSizeA.equals(selectedColumnSizeB);
            default:
                return true;
        }
    }
}
