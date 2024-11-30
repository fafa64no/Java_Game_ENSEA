package main.rendering.sprites;

import main.physics.dynamic_objects.DynamicPoint;
import main.utils.containers.SizedTexture;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public class Sprite {
    protected final SizedTexture texture;

    protected final DynamicPoint parent;

    public Sprite(SizedTexture texture, DynamicPoint parent) {
        this.texture = texture;
        this.parent = parent;
    }

    public BufferedImage getCurrentTexture() {
        return texture.texture;
    }

    public Vec2 getPosition() {
        return parent.getPosition();
    }

    public double getRotation() {
        return parent.getRotation();
    }
}
