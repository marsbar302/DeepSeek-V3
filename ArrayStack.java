import java.util.Arrays;

public class ArrayStack<T> implements StackADT<T> {
    private T[] array;
    private int top;
    
    public ArrayStack() {
        this(10);
    }
    
    @SuppressWarnings("unchecked")
    public ArrayStack(int initCapacity) {
        array = (T[]) new Object[initCapacity];
        top = initCapacity - 1;
    }
    
    public void push(T element) {
        if (top < 0) {
            expandCapacity();
        }
        array[top--] = element;
    }
    
    public T pop() {
        if (isEmpty()) {
            throw new CollectionException("Stack is empty");
        }
        return array[++top];
    }
    
    public T peek() {
        if (isEmpty()) {
            throw new CollectionException("Stack is empty");
        }
        return array[top + 1];
    }
    
    public boolean isEmpty() {
        return top == array.length - 1;
    }
    
    public int size() {
        return array.length - 1 - top;
    }
    
    public int getCapacity() {
        return array.length;
    }
    
    public int getTop() {
        return top;
    }
    
    public String toString() {
        if (isEmpty()) {
            return "Empty stack.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = top + 1; i < array.length; i++) {
            sb.append(array[i]).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }
    
    @SuppressWarnings("unchecked")
    private void expandCapacity() {
        int newCapacity = (array.length <= 15) ? array.length * 2 : array.length + 10;
        T[] newArray = (T[]) new Object[newCapacity];
        System.arraycopy(array, top + 1, newArray, newCapacity - (array.length - top - 1), array.length - top - 1);
        top = newCapacity - (array.length - top - 1) - 1;
        array = newArray;
    }
}