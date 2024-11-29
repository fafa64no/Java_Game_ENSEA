package main.game.characters.cubes;

import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public abstract class DecorativeFollowerCube extends BasicCube{
    protected final RedCube parent;

    protected final Vec2 initialPosition;
    protected final double initialRotation = 0;
    protected double rotation = 0;
    protected final Vec2 rotationCenter;

    protected DecorativeFollowerCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, int textureSize) {
        super(
                position,
                texture,
                deadTexture,
                100,
                textureSize
        );
        this.parent = null;
        this.initialPosition = position;
        this.rotationCenter = new Vec2(textureSize*0.5);
    }

    protected DecorativeFollowerCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, double maxHealth, int textureSize) {
        super(
                position,
                texture,
                deadTexture,
                maxHealth,
                textureSize
        );
        this.parent = null;
        this.initialPosition = position;
        this.rotationCenter = new Vec2(textureSize*0.5);
    }

    protected DecorativeFollowerCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, int textureSize, Vec2 rotationCenter) {
        super(
                position,
                texture,
                deadTexture,
                100,
                textureSize
        );
        this.parent = null;
        this.initialPosition = position;
        this.rotationCenter = rotationCenter;
    }

    protected DecorativeFollowerCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, double maxHealth, int textureSize, Vec2 rotationCenter) {
        super(
                position,
                texture,
                deadTexture,
                maxHealth,
                textureSize
        );
        this.parent = null;
        this.initialPosition = position;
        this.rotationCenter = rotationCenter;
    }

    public void updatePos(){
        if(parent == null)return;
        position = Vec2.add(parent.getPosition(),initialPosition);
    }

    public void updateRotation(){
        rotation = initialRotation + parent.getRotation();
    }

    public Vec2 getRotationCenter() {
        return rotationCenter;
    }

    @Override
    protected void genColliders() {

    }

    @Override
    public double getRotation() {
        return rotation;
    }
}
