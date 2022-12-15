package aoc.solutions.standard;

import java.util.Objects;

public class Pair<L, R> {

    private L left;
    private R right;

    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L left() {
        return left;
    }

    public R right() {
        return right;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    @Override
    public String toString() {
        return "Pair<" + left + ", " + right + ">";
    }

    @Override
    public boolean equals(Object o) {

        if(this == o) return true;

        if(o instanceof Pair p) {
            return this.left.equals(p.left) && this.right.equals(p.right);
        }

        return false;

    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

}
