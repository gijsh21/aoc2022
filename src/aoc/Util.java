package aoc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {

    public static Scanner readFile(String path) {

        Scanner sc = null;
        try {
            File f = new File(path);
            sc = new Scanner(f);
        } catch(Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

        return sc;

    }

    public static List<String> lines(String path) {

        Scanner sc = readFile(path);

        List<String> res = new ArrayList<>();
        while(sc.hasNextLine()) {
            res.add(sc.nextLine());
        }
        sc.close();

        return res;

    }

    public static List<String> inputLines(String packageName) {

        return lines("src/aoc/solutions/" + packageName + "/input.txt");

    }

    public static List<String> testLines(String packageName) {

        return lines("src/aoc/solutions/" + packageName + "/test.txt");

    }

    public static List<Integer> getInts(String s) {

        List<Integer> result = new ArrayList<>();
        char[] input = s.toCharArray();

        StringBuilder curr = new StringBuilder();
        for(char c : input) {
            if(c >= '0' && c <= '9') {
                curr.append(c);
            } else {
                if(curr.length() > 0) {
                    result.add(Integer.parseInt(curr.toString()));
                    curr = new StringBuilder();
                }
            }
        }

        if(curr.length() > 0) {
            result.add(Integer.parseInt(curr.toString()));
        }

        return result;

    }

    public static List<Long> getLongs(String s) {

        List<Long> result = new ArrayList<>();
        char[] input = s.toCharArray();

        StringBuilder curr = new StringBuilder();
        for(char c : input) {
            if(c >= '0' && c <= '9') {
                curr.append(c);
            } else {
                if(curr.length() > 0) {
                    result.add(Long.parseLong(curr.toString()));
                    curr = new StringBuilder();
                }
            }
        }

        if(curr.length() > 0) {
            result.add(Long.parseLong(curr.toString()));
        }

        return result;

    }

    public static String arrayToString(byte[] arr) {

        if(arr.length <= 0) return "";

        Byte[] objArr = new Byte[arr.length];
        for(int i = 0; i < arr.length; i++) {
            objArr[i] = arr[i];
        }

        return arrayToString(objArr);

    }

    public static String arrayToString(char[] arr) {

        if(arr.length <= 0) return "";

        Character[] objArr = new Character[arr.length];
        for(int i = 0; i < arr.length; i++) {
            objArr[i] = arr[i];
        }

        return arrayToString(objArr);

    }

    public static String arrayToString(short[] arr) {

        if(arr.length <= 0) return "";

        Short[] objArr = new Short[arr.length];
        for(int i = 0; i < arr.length; i++) {
            objArr[i] = arr[i];
        }

        return arrayToString(objArr);

    }

    public static String arrayToString(int[] arr) {

        if(arr.length <= 0) return "";

        Integer[] objArr = new Integer[arr.length];
        for(int i = 0; i < arr.length; i++) {
            objArr[i] = arr[i];
        }

        return arrayToString(objArr);

    }

    public static String arrayToString(long[] arr) {

        if(arr.length <= 0) return "";

        Long[] objArr = new Long[arr.length];
        for(int i = 0; i < arr.length; i++) {
            objArr[i] = arr[i];
        }

        return arrayToString(objArr);

    }

    public static String arrayToString(float[] arr) {

        if(arr.length <= 0) return "";

        Float[] objArr = new Float[arr.length];
        for(int i = 0; i < arr.length; i++) {
            objArr[i] = arr[i];
        }

        return arrayToString(objArr);

    }

    public static String arrayToString(double[] arr) {

        if(arr.length <= 0) return "";

        Double[] objArr = new Double[arr.length];
        for(int i = 0; i < arr.length; i++) {
            objArr[i] = arr[i];
        }

        return arrayToString(objArr);

    }

    public static String arrayToString(boolean[] arr) {

        if(arr.length <= 0) return "";

        Boolean[] objArr = new Boolean[arr.length];
        for(int i = 0; i < arr.length; i++) {
            objArr[i] = arr[i];
        }

        return arrayToString(objArr);

    }

    public static <T> String arrayToString(T[] arr) {

        if(arr.length <= 0) return "";

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for(T t : arr) {
            sb.append(t).append(", ");
        }

        return sb.substring(0, sb.length() - 2) + "]";

    }

}
