package main.rendering.sprites;

import main.game.GameEngine;
import main.physics.dynamic_objects.DynamicPoint;
import main.rendering.layers.RenderingLayer;
import main.utils.containers.SizedTexture;
import main.utils.vectors.Vec2;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Sprite {
    protected final SizedTexture texture;

    protected DynamicPoint parent;

    protected final RenderingLayer renderingLayer;

    public Sprite(SizedTexture texture, RenderingLayer renderingLayer) {
        this.texture = texture;
        this.parent = null;
        this.renderingLayer = renderingLayer;
    }

    public Sprite(SizedTexture texture, DynamicPoint parent, RenderingLayer renderingLayer) {
        this.texture = texture;
        this.parent = parent;
        this.renderingLayer = renderingLayer;
    }

    public void addToRenderList() {
        GameEngine.getCurrentLevel().getSpriteRenderer().addSpriteToRenderList(this,renderingLayer);
    }

    public void removeFromRenderList() {
        GameEngine.getCurrentLevel().getSpriteRenderer().removeSprite(this);
    }

    public BufferedImage getCurrentTexture() {
        return texture.texture;
    }

    public Vec2 getPosition() {
        return parent.getPosition();
    }

    public AffineTransform getAffineTransform() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(
            parent.getPosition().x - 0.5 * getTextureSize(),
            parent.getPosition().y - 0.5 * getTextureSize()
        );
        affineTransform.rotate(
            parent.getRotation(),
            0.5 * getTextureSize(),
            0.5 * getTextureSize()
        );
        return affineTransform;
    }

    public void setParent(DynamicPoint parent) {
        if (this.parent == null) {
            this.parent = parent;
        } else {
            System.out.println("Trying to overwrite parent.");
        }
    }

    public int getTextureSize() {
        return texture.textureSize;
    }
}
