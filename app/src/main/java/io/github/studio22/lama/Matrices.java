package io.github.studio22.lama;

import java.io.File;

public class Matrices {
    public double[][] getMatrix() {
        return matrix;
    }

    private final double[][] matrix;
    public static File internalStorageDir; //путь к директории с файлома ламы

    Matrices(double[][] matrix){
        this.matrix = matrix;
    }
}

