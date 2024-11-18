package rendering;

public interface Displayable {
    void draw(RenderEngine renderEngine);
    void clear(RenderEngine renderEngine);
    void linkCamera(Camera camera);
}
