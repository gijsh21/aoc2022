package aoc.solutions.day7;

import aoc.Util;
import aoc.solutions.standard.Tree;

import java.util.*;

public class D7P1 {

    private static abstract class FileSystemObject {

        String name;

        FileSystemObject(String name) {
            this.name = name;
        }

    }

    private static class File extends FileSystemObject {

        long size;

        File(String name, long size) {
            super(name);
            this.size = size;
        }

    }

    private static class Directory extends FileSystemObject {

        UUID uuid;

        Directory(String name) {
            super(name);
            uuid = UUID.randomUUID();
        }

    }

    private static long calculateSizes(Tree<FileSystemObject> rootDirectory, HashMap<Directory, Long> map) {

        if(!(rootDirectory.getElement() instanceof Directory)) {
            if(rootDirectory.getElement() instanceof File f) {
                return f.size;
            }
            return 0L;
        }

        long curr = 0L;

        for(Tree<FileSystemObject> node : rootDirectory.getChildren()) {

            FileSystemObject fObj = node.getElement();
            if(fObj instanceof File f) {
                curr += f.size;
            } else if(fObj instanceof Directory d) {
                curr += calculateSizes(node, map);
            }

        }

        map.put((Directory) rootDirectory.getElement(), curr);

        return curr;

    }

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day7/input.txt");

        List<String> lines = new ArrayList<>();
        while(sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        sc.close();

        Tree<FileSystemObject> root = new Tree<>();
        root.setElement(new Directory("/"));
        buildTree(lines, root);

        HashMap<Directory, Long> map = new HashMap<>();
        calculateSizes(root, map);

        long sum = 0L;
        for(Map.Entry<Directory, Long> entry : map.entrySet()) {
            if(entry.getValue() <= 100000L) {
                sum += entry.getValue();
            }
        }

        System.out.println(sum);

    }

    private static void buildTree(List<String> lines, Tree<FileSystemObject> root) {

        for(String line : lines) {

        }

    }

}
