package id.umm.uts.struktur;

public class SinglyLinkedList<T> implements StrukturData<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;
    private int langkahTerakhir;

    @Override
    public void add(T value) { addLast(value); }

    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void insert(int position, T value) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Posisi tidak valid: " + position);
        }
        if (position == 0) { addFirst(value); return; }
        if (position == size) { addLast(value); return; }

        Node<T> newNode = new Node<>(value);
        Node<T> current = head;
        for (int i = 0; i < position - 1; i++) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    @Override
    public T get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Indeks di luar batas: " + position);
        }
        Node<T> current = head;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public T remove(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Indeks di luar batas: " + position);
        }
        T removedData;
        if (position == 0) {
            removedData = head.data;
            head = head.next;
            if (head == null) tail = null;
        } else {
            Node<T> prev = head;
            for (int i = 0; i < position - 1; i++) {
                prev = prev.next;
            }
            removedData = prev.next.data;
            prev.next = prev.next.next;
            if (prev.next == null) tail = prev;
        }
        size--;
        return removedData;
    }

    public int search(T target) {
        langkahTerakhir = 0;
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            langkahTerakhir++;
            if (current.data != null && current.data.equals(target)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    public int getLangkahTerakhir() { return langkahTerakhir; }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public boolean contains(T value) { return search(value) != -1; }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean validateInvariant() {
        if (size == 0) return head == null && tail == null;
        if (head == null || tail == null || tail.next != null) return false;
        int count = 0;
        Node<T> current = head;
        Node<T> lastVisited = null;
        while (current != null) {
            count++;
            lastVisited = current;
            current = current.next;
            if (count > size) return false;
        }
        return count == size && lastVisited == tail;
    }
}