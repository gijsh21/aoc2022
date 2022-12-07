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
        List<FileSystemObject> children;

        Directory(String name) {
            super(name);
            uuid = UUID.randomUUID();
            children = new ArrayList<>();
        }

    }

    private static long calculateSizes(FileSystemObject rootDirectory, HashMap<Directory, Long> map) {

        if(!(rootDirectory instanceof Directory)) {
            if(rootDirectory instanceof File f) {
                return f.size;
            }
            return 0L;
        }

        long curr = 0L;

        for(FileSystemObject fObj : ((Directory) rootDirectory).children) {

            if(fObj instanceof File f) {
                curr += f.size;
            } else if(fObj instanceof Directory d) {
                curr += calculateSizes(fObj, map);
            }

        }

        map.put((Directory) rootDirectory, curr);

        return curr;

    }

    public static void run() {

        Scanner sc = Util.readFile("src/aoc/solutions/day7/input.txt");

        List<String> lines = new ArrayList<>();
        while(sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        lines.remove(0);

        sc.close();

        Directory root = new Directory("/");
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

    private static void buildTree(List<String> lines, Directory curr) {

        for(int i = 0; i < lines.size(); i++) {

            String line = lines.get(i);

            if(line.startsWith("$ ls")) {

                i++;
                line = lines.get(i);
                while(!line.startsWith("$")) {
                    if(line.startsWith("dir")) {

                    } else {
                        
                    }

                    i++;
                    line = lines.get(i);
                }

                i--;

            } else if(line.startsWith("$ cd")) {

                String goToDirectory = line.split(" ")[2];
                Optional<FileSystemObject> oF = curr.children.stream()
                        .filter(c -> c instanceof Directory && c.name.equals(goToDirectory)).findFirst();
                if(oF.isEmpty()) throw new IllegalArgumentException();
                curr = (Directory) oF.get();

            }

        }

    }

}
