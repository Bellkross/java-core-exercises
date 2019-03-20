package ua.procamp.bst;

import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private Node<T> root;
    private int size;

    public RecursiveBinarySearchTree() {
        root = new Node<>();
        size = 0;
    }

    @Override
    public boolean insert(T element) {
        boolean isInserted = insert(root, element);

        if (isInserted) ++size;

        return isInserted;
    }

    private boolean insert(Node<T> target, T element) {
        boolean isInserted = false;
        if (size() == 0) {
            target.value = element;
            return true;
        }

        if (less(element, target.value)) {
            if (isNull(target.left)) {
                target.left = new Node<>(element);
                isInserted = true;
            } else {
                isInserted = insert(target.left, element);
            }
        }

        if (less(target.value, element)) {
            if (isNull(target.right)) {
                target.right = new Node<>(element);
                isInserted = true;
            } else {
                isInserted = insert(target.right, element);
            }
        }

        return isInserted;
    }

    @Override
    public boolean search(T element) {
        return search(root, element);
    }

    private boolean search(Node<T> target, T element) {
        if (isNull(target) || isNull(target.value)) {
            return false;
        }

        return target.value.equals(element) || search(target.left, element) || search(target.right, element);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int height() {
        return height(root);
    }

    private int height(Node<T> target) {
        boolean isEmpty = isNull(target) || isNull(target.value);
        if (isEmpty || (isNull(target.left) && isNull(target.right))) {
            return 0;
        }
        return 1 + Math.max(height(target.left), height(target.right));
    }


    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        inOrderTraversal(root, consumer);
    }

    private void inOrderTraversal(Node<T> target, Consumer<T> consumer) {
        if (nonNull(target.left)) {
            inOrderTraversal(target.left, consumer);
        }

        if (nonNull(target) && nonNull(target.value)) {
            consumer.accept(target.value);
        }

        if (nonNull(target.right)) {
            inOrderTraversal(target.right, consumer);
        }
    }

    private boolean less(T l, T r) {
        return l.compareTo(r) < 0;
    }

    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        public Node() {
            this(null);
        }

        public Node(T value) {
            this.value = value;
            left = null;
            right = null;
        }

    }

}
