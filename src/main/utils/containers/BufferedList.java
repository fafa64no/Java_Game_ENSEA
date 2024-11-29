package main.utils.containers;

import java.util.ArrayList;
import java.util.List;

public class BufferedList<T> {
    public final List<T> elements   = new ArrayList<>();

    private List<T> listToAdd       = new ArrayList<>();
    private List<T> listToRemove    = new ArrayList<>();

    public void flush() {
        elements.addAll(listToAdd);
        elements.removeAll(listToRemove);
        listToAdd       = new ArrayList<>();
        listToRemove    = new ArrayList<>();
    }

    public void addElement(T element) {
        listToAdd.add(element);
    }

    public void removeElement(T element) {
        listToRemove.add(element);
    }
}
