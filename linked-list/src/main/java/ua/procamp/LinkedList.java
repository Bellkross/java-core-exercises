package ua.procamp;

import java.util.Arrays;
import java.util.Objects;

/**
 * {@link LinkedList} is a list implementation that is based on singly linked generic nodes. A node is implemented as
 * inner static class {@link Node<T>}. In order to keep track on nodes, {@link LinkedList} keeps a reference to a head node.
 *
 * @param <T> generic type parameter
 */
public class LinkedList<T> implements List<T> {

    private Node<T> head;
    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    /**
     * This method creates a list of provided elements
     *
     * @param elements elements to add
     * @param <T>      generic type
     * @return a new list of elements the were passed as method parameters
     */
    public static <T> List<T> of(T... elements) {
        final LinkedList<T> linkedList = new LinkedList<>();
        Arrays.stream(elements).forEach(linkedList::add);
        return linkedList;
    }

    /**
     * Adds an element to the end of the list
     *
     * @param element element to add
     */
    @Override
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null)
            head = newNode;
        else
            getLastNode().next = newNode;
        ++size;
    }

    /**
     * Adds a new element to the specific position in the list. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an index of new element
     * @param element element to add
     */
    @Override
    public void add(int index, T element) {
        Objects.checkFromIndexSize(0, index, size());
        Node<T> newNode = new Node<>(element);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> current = getNodeAt(index - 1);
            if (current.next != null)
                newNode.next = current.next;
            current.next = newNode;
        }
        ++size;
    }

    /**
     * Changes the value of an list element at specific position. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an position of element to change
     * @param element a new element value
     */
    @Override
    public void set(int index, T element) {
        Objects.checkFromIndexSize(0, index, size());
        if (isEmpty())
            throw new IndexOutOfBoundsException();
        getNodeAt(index).setValue(element);
    }


    private Node<T> getLastNode() {
        return getNodeAt(size() - 1);
    }

    private Node<T> getNodeAt(int index) {
        int counter = 0;
        Node<T> current = head;
        while (counter != index) {
            ++counter;
            current = current.next;
            if (current == null)
                throw new IndexOutOfBoundsException();
        }
        return current;
    }

    /**
     * Retrieves an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return an element value
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        return getNodeAt(index).getValue();
    }

    /**
     * Removes an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     */
    @Override
    public void remove(int index) {
        if (head == null)
            throw new IndexOutOfBoundsException();
        if (index == 0) {
            head = head.next;
            return;
        }
        Node<T> prev = getNodeAt(index - 1);
        Node<T> targetNode = getNodeAt(index);
        prev.next = targetNode.next;
    }


    /**
     * Checks if a specific exists in he list
     *
     * @return {@code true} if element exist, {@code false} otherwise
     */
    @Override
    public boolean contains(T element) {
        Node<T> current = head;
        while (current != null) {
            if (current.value.equals(element))
                return true;
            current = current.next;
        }
        return false;
    }

    /**
     * Checks if a list is empty
     *
     * @return {@code true} if list is empty, {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return head == null || head.value == null;
    }

    /**
     * Returns the number of elements in the list
     *
     * @return number of elements
     */
    @Override
    public int size() {
        int size = 0;
        Node<T> curr = head;
        while (curr != null) {
            ++size;
            curr = curr.next;
        }
        return size;
    }

    /**
     * Removes all list elements
     */
    @Override
    public void clear() {
        head = null;
    }

    private static class Node<T> {

        private T value;
        private Node<T> next;

        public Node(final T value) {
            this(value, null);
        }

        public Node(final T value, final Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public void setValue(final T value) {
            this.value = value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(final Node<T> next) {
            this.next = next;
        }
    }
}
