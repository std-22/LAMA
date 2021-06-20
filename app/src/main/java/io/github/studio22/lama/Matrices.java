package io.github.studio22.lama;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Matrices {
    public static int code;

    public double[][] getMatrix() {
        return matrix;
    }

    private final double[][] matrix;
    public static File internalStorageDir;

    static {
        File history = new File(internalStorageDir, "history.txt");

        try{
            Scanner scanner = new Scanner(history);
            while(scanner.hasNext()){
                String[] elements = scanner.nextLine().split(" ");
                code = Integer.parseInt(elements[0]);
            }
        }catch(FileNotFoundException e){
            System.out.println(e.getClass());
        }
    }

    Matrices(double[][] matrix){
        this.matrix = matrix;
    }
}

