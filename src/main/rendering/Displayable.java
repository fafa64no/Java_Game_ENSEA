package main.rendering;

public interface Displayable {
    void draw();
    void clear();

    void addToRenderList();
    void removeFromRenderList();
}
