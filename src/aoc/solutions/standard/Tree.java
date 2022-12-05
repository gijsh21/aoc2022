package aoc.solutions.standard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree<T> {

    public static class TreeIterator<T> {

        public enum IteratorMode {
            BFS,
            DFS
        }

        private Tree<T> curr;
        private IteratorMode mode;

        private Queue<Tree<T>> bfsQueue;
        private Stack<Tree<T>> dfsStack;

        public TreeIterator(Tree<T> tree) {
            this(tree, IteratorMode.BFS);
        }

        public TreeIterator(Tree<T> tree, IteratorMode mode) {
            this.curr = tree;
            this.mode = mode;
            this.bfsQueue = new LinkedList<>();
            this.dfsStack = new Stack<>();
        }

        public boolean hasNext() {
            return curr != null;
        }

        public T next() {

            if(!hasNext()) throw new IllegalStateException();

            T toReturn = curr.element;
            if(mode == IteratorMode.BFS) {
                bfsQueue.addAll(curr.children);
                curr = bfsQueue.poll();
            } else if(mode == IteratorMode.DFS) {
                dfsStack.pushAllReverse(curr.children);
                curr = dfsStack.pop();
            }

            return toReturn;

        }

    }

    private T element;
    private List<Tree<T>> children;

    public Tree() {
        this(null, List.of());
    }

    public Tree(T element) {
        this(element, List.of());
    }

    public Tree(List<Tree<T>> children) {
        this(null, children);
    }

    public Tree(T element, List<Tree<T>> children) {
        this.element = element;
        this.children = new ArrayList<>(children);
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public void addChild(Tree<T> child) {
        this.children.add(child);
    }

    public void removeChild(Tree<T> child) {
        this.children.remove(child);
    }

    public void removeChild(int index) {
        this.children.remove(index);
    }

    public void setChild(int index, Tree<T> child) {
        this.children.set(index, child);
    }

    public Tree<T> getChild(int index) {
        return this.children.get(index);
    }

    public int childAmount() {
        return this.children.size();
    }

    public TreeIterator<T> bfsIterator() {
        return new TreeIterator<>(this, TreeIterator.IteratorMode.BFS);
    }

    public TreeIterator<T> dfsIterator() {
        return new TreeIterator<>(this, TreeIterator.IteratorMode.DFS);
    }

    @Override
    public String toString() {

        if(children.size() <= 0) {
            if(element == null) return "[]";
            return "[" + element + "]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(element).append(", ");

        for(Tree<T> child : children) {
            sb.append(child.toString()).append(", ");
        }

        return sb.substring(0, sb.length() - 2) + "]";

    }

}
