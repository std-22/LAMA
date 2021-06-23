package io.github.studio22.lama;

import java.io.File;

public class Matrices {
    public double[][] getMatrix() {
        return matrix;
    }

    private final double[][] matrix;
    public static File internalStorageDir;

    Matrices(double[][] matrix){
        this.matrix = matrix;
    }
}

