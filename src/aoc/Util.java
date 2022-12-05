package aoc;

import java.io.File;
import java.util.Scanner;

public class Util {

    public static Scanner readFile(String path) {

        Scanner sc = null;
        try {
            File f = new File(path);
            sc = new Scanner(f);
        } catch(Exception e) {
            throw new IllegalArgumentException();
        }

        return sc;

    }

}
