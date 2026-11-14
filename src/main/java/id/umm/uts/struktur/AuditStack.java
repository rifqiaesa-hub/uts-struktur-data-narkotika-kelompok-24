package id.umm.uts.struktur;

public class AuditStack<T> {
    private Object[] elements;
    private int top;

    public AuditStack() {
        this.elements = new Object[16];
        this.top = -1;
    }

    public void push(T item) {
        if (top + 1 == elements.length) {
            resize(elements.length * 2);
        }
        elements[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) throw new IllegalStateException("Stack kosong");
        T item = (T) elements[top];
        elements[top--] = null;
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) throw new IllegalStateException("Stack kosong");
        return (T) elements[top];
    }

    public boolean searchPreserve(String keyword) {
        AuditStack<T> tempStack = new AuditStack<>();
        boolean found = false;

        while (!this.isEmpty()) {
            T current = this.pop();
            tempStack.push(current);
            if (current != null && current.toString().toLowerCase().contains(keyword.toLowerCase())) {
                found = true;
            }
        }

        while (!tempStack.isEmpty()) {
            this.push(tempStack.pop());
        }
        return found;
    }

    @SuppressWarnings("unchecked")
    public T getAt(int index) {
        if (index < 0 || index > top) throw new IndexOutOfBoundsException("Indeks invalid: " + index);
        return (T) elements[index];
    }

    public int size() { return top + 1; }
    public boolean isEmpty() { return top == -1; }

    private void resize(int newCapacity) {
        Object[] newArray = new Object[newCapacity];
        for (int i = 0; i <= top; i++) newArray[i] = elements[i];
        elements = newArray;
    }

    public boolean validateInvariant() {
        if (top < -1 || top >= elements.length) return false;
        for (int i = top + 1; i < elements.length; i++) {
            if (elements[i] != null) return false;
        }
        return true;
    }
}