package aoc.solutions.standard;

import java.util.ArrayList;
import java.util.List;

public class Stack<T> {

    private List<T> list;

    public Stack() {
        this.list = new ArrayList<>();
    }

    public T pop() {
        if(list.size() <= 0) return null;
        T toReturn = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return toReturn;
    }

    public List<T> pop(int amount) {
        List<T> res = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            res.add(pop());
        }
        return res;
    }

    public void push(T elem) {
        list.add(elem);
    }

    public void pushAll(List<T> elems) {
        for(T t : elems) {
            push(t);
        }
    }

    public void pushAllReverse(List<T> elems) {
        for(int i = elems.size() - 1; i >= 0; i--) {
            push(elems.get(i));
        }
    }

    public int size() {
        return list.size();
    }

    public void reverse() {

        if(size() <= 1) return;

        List<T> temp = new ArrayList<>();
        while(size() > 0) temp.add(pop());

        pushAll(temp);

    }

    public static <T> Stack<T> fromList(List<T> list) {

        Stack<T> stack = new Stack<>();
        for(T t : list) {
            stack.push(t);
        }

        return stack;

    }

    public static <T> Stack<T> fromListReverse(List<T> list) {

        Stack<T> stack = new Stack<>();
        for(int i = list.size() - 1; i >= 0; i--) {
            stack.push(list.get(i));
        }

        return stack;

    }

    @Override
    public String toString() {

        return "Stack" + list.toString();

    }

}
