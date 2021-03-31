package io.github.studio22.lama;

/**
 * Класс, проверяющий корректность размерности введенной матрицы
 */
public class CheckSize {
    public static boolean checkSize(String nameOfFunction, String selectedRowSize, String selectedColumnSize) {
        switch (nameOfFunction) {
            case "Критерий Сильвестра":
                return selectedRowSize.equals(selectedColumnSize);
            case "Транспонирование":
                return selectedColumnSize.equals(selectedRowSize);
            default:
                return true;
        }
    }
}
