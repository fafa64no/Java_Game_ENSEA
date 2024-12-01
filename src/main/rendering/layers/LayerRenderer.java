package main.rendering.layers;

import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.utils.containers.BufferedList;

public class LayerRenderer {
    private final BufferedList<Displayable> renderingLayer = new BufferedList<>();

    public void drawLayer() {
        for (Displayable displayable : renderingLayer.elements) {
            displayable.draw();
            RenderEngine.getInstance().setVisible(true);
        }
    }

    public void addToRenderList(Displayable displayable) {
        renderingLayer.addElement(displayable);
    }

    public void removeFromRenderList(Displayable displayable) {
        renderingLayer.removeElement(displayable);
    }

    public void flushRenderList() {
        renderingLayer.flush();
    }
}
