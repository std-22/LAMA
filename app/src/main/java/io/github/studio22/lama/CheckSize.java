package io.github.studio22.lama;

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
