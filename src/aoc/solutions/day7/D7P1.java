package aoc.solutions.day7;

import aoc.Util;

import java.util.*;

public class D7P1 {

    private static abstract class FileSystemObject {

        String name;
        FileSystemObject parent;

        FileSystemObject(String name, FileSystemObject parent) {
            this.name = name;
            this.parent = parent;
        }

    }

    private static class File extends FileSystemObject {

        long size;

        File(String name, long size, FileSystemObject parent) {
            super(name, parent);
            this.size = size;
        }

    }

    private static class Directory extends FileSystemObject {

        UUID uuid;
        List<FileSystemObject> children;

        Directory(String name, FileSystemObject parent) {
            super(name, parent);
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

        Directory root = new Directory("/", null);
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

                if(i >= lines.size() - 1) break;

                i++;
                line = lines.get(i);
                while(!line.startsWith("$")) {
                    if(line.startsWith("dir")) {
                        String[] split = line.split(" ");
                        Optional<FileSystemObject> oF = curr.children.stream()
                                .filter(c -> c instanceof Directory && c.name.equals(split[1]))
                                .findFirst();
                        if(oF.isEmpty()) {
                            curr.children.add(new Directory(split[1], curr));
                        }
                    } else {
                        String[] split = line.split(" ");
                        Optional<FileSystemObject> oF = curr.children.stream()
                                .filter(c -> c instanceof File && c.name.equals(split[1]))
                                .findFirst();
                        if(oF.isEmpty()) {
                            curr.children.add(new File(split[1], Long.parseLong(split[0]), curr));
                        }
                    }

                    if(i >= lines.size() - 1) break;
                    i++;
                    line = lines.get(i);
                }

                if(i >= lines.size() - 1) break;
                i--;

            } else if(line.startsWith("$ cd")) {

                String goToDirectory = line.split(" ")[2];

                if(goToDirectory.equals("..")) {
                    if(!(curr.parent instanceof Directory)) throw new IllegalArgumentException();
                    curr = (Directory) curr.parent;
                    continue;
                }

                Optional<FileSystemObject> oF = curr.children.stream()
                        .filter(c -> c instanceof Directory && c.name.equals(goToDirectory)).findFirst();
                if(oF.isEmpty()) throw new IllegalArgumentException(goToDirectory);
                curr = (Directory) oF.get();

            }

        }

    }

}
