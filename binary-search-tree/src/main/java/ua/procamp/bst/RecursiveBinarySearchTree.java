package ua.procamp.bst;

import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private T value;
    private RecursiveBinarySearchTree<T> left;
    private RecursiveBinarySearchTree<T> right;
    private int size;

    public RecursiveBinarySearchTree() {
        value = null;
        left = null;
        right = null;
        size = 0;
    }

    public RecursiveBinarySearchTree(T value) {
        this.value = value;
        left = null;
        right = null;
        size = 1;
    }

    @Override
    public boolean insert(T element) {
        boolean result = false;
        if (size() == 0) {
            value = element;
            ++size;
            return true;
        }

        if (less(element, value)) {
            if (isNull(left)) {
                left = new RecursiveBinarySearchTree<>(element);
                result = true;
            } else {
                result = left.insert(element);
            }
        } else if (less(value, element)) {
            if (isNull(right)) {
                right = new RecursiveBinarySearchTree<>(element);
                result = true;
            } else {
                result = right.insert(element);
            }
        }
        if (result) ++size;
        return result;
    }

    @Override
    public boolean search(T element) {
        if (isNull(value)) return false;
        if (value.equals(element)) return true;

        if (less(element, value)) {
            if (isNull(left)) {
                return false;
            } else {
                return left.search(element);
            }
        } else {
            if (isNull(right)) {
                return false;
            } else {
                return right.search(element);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int height() {
        if (isNull(value)) return 0;
        if (isNull(left) && isNull(right)) {
            return 0;
        }
        if (nonNull(left) && nonNull(right)) {
            return 1 + Math.max(left.height(), right.height());
        }
        if (nonNull(left)) {
            return 1 + left.height();
        } else {
            return 1 + right.height();
        }
    }


    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        if (nonNull(left)) {
            left.inOrderTraversal(consumer);
        }

        if (nonNull(value)) {
            consumer.accept(value);
        }

        if (nonNull(right)) {
            right.inOrderTraversal(consumer);
        }
    }

    private boolean less(T l, T r) {
        return l.compareTo(r) < 0;
    }

}
