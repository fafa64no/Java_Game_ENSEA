package rendering;

import javax.swing.*;

public interface Displayable {
    void draw(RenderEngine renderEngine);
    void clear(RenderEngine renderEngine);
}
