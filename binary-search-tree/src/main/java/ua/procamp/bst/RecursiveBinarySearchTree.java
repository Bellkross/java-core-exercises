package ua.procamp.bst;

import java.util.function.Consumer;

import static java.util.Objects.isNull;

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
        if (size() == 0) {
            target.value = element;
            return true;
        }
        if (less(element, target.value)) {
            return insertLeft(target, element);
        }
        if (less(target.value, element)) {
            return insertRight(target, element);
        }
        return false;
    }

    private boolean insertLeft(Node<T> target, T element) {
        if (isNull(target.left)) {
            target.left = new Node<>(element);
            return true;
        } else {
            return insert(target.left, element);
        }
    }

    private boolean insertRight(Node<T> target, T element) {
        if (isNull(target.right)) {
            target.right = new Node<>(element);
            return true;
        } else {
            return insert(target.right, element);
        }
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
        if (isNull(target) || isNull(target.value)) {
            return;
        }
        inOrderTraversal(target.left, consumer);
        consumer.accept(target.value);
        inOrderTraversal(target.right, consumer);
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
