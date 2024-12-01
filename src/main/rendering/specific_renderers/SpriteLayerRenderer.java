package main.rendering.specific_renderers;

import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.rendering.layers.RenderingLayer;
import main.rendering.sprites.Sprite;
import main.utils.containers.BufferedList;
import main.utils.vectors.Vec2;

import javax.swing.*;
import java.awt.*;

public class SpriteLayerRenderer extends JPanel implements Displayable {
    private final RenderingLayer renderingLayer;

    private final BufferedList<Sprite> spriteBufferedList = new BufferedList<>();

    public SpriteLayerRenderer(RenderingLayer renderingLayer) {
        this.renderingLayer = renderingLayer;

        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        RenderEngine.getCurrentCamera().transformGraphicsToCamera(g2d);

        for (Sprite sprite : spriteBufferedList.elements) {
            if(
                RenderEngine
                    .getCurrentCamera()
                    .getDisplayWindow(new Vec2(sprite.getTextureSize()))
                    .contains(sprite.getPosition())
            ) {
                g2d.drawRenderedImage(
                    sprite.getCurrentTexture(),
                    sprite.getAffineTransform()
                );
            }
        }
    }

    public void addSprite(Sprite sprite) {
        spriteBufferedList.addElement(sprite);
    }

    public void removeSprite(Sprite sprite) {
        spriteBufferedList.removeElement(sprite);
    }

    public void flushList() {
        spriteBufferedList.flush();
    }

    @Override
    public void draw() {
        RenderEngine.getInstance().remove(this);
        RenderEngine.getInstance().add(this);
    }

    @Override
    public void clear() {
        RenderEngine.getInstance().remove(this);
    }

    @Override
    public void addToRenderList() {
        RenderEngine.addToRenderList(this, renderingLayer);
    }

    @Override
    public void removeFromRenderList() {
        RenderEngine.removeFromRenderList(this);
    }

}
