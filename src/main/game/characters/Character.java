package main.game.characters;

import main.game.DynamicSprite;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public abstract class Character extends DynamicSprite {
    private final double velocityMultiplier;

    protected Vec2 currentDir = new Vec2();
    protected final Vec2 previousDir = new Vec2();
    protected final IVec2 textureSize;

    protected int animationCounter;
    protected final int animationFrames;
    protected final int animationSpeed=4;

    public Character(Vec2 position, String texturePath, double velocityMultiplier, int animationFrames, IVec2 textureSize) {
        super(position, texturePath);
        this.velocityMultiplier = velocityMultiplier;
        this.animationFrames=animationFrames;
        this.textureSize=textureSize;
    }

    public Character(Vec2 position, BufferedImage texture, double velocityMultiplier, int animationFrames, IVec2 textureSize) {
        super(position, texture);
        this.velocityMultiplier = velocityMultiplier;
        this.animationFrames=animationFrames;
        this.textureSize=textureSize;
    }

    public void setInput(Vec2 vel) {
        super.setVelocity(Vec2.multiply(vel,this.velocityMultiplier));
        if (this.currentDir.x!=0||this.currentDir.y!=0){
            this.previousDir.x=this.currentDir.x;
            this.previousDir.y=this.currentDir.y;
        }
        this.currentDir=vel;
    }
}
