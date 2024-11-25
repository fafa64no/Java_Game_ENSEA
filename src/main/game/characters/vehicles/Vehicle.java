package main.game.characters.vehicles;

import main.game.characters.Character;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

public abstract class Vehicle extends Character {
    protected Vec2 scale;
    protected double rotation=0;
    protected final double rotationSpeed;
    public Vehicle(Vec2 position, String texturePath, int velocityMultiplier, int animationFrames, IVec2 textureSize, double rotationSpeed) {
        super(position, texturePath, velocityMultiplier, animationFrames,textureSize);
        this.scale=new Vec2(1,1);
        this.rotationSpeed=rotationSpeed;
    }

    public Vehicle(Vec2 position, String texturePath, int velocityMultiplier, int animationFrames, IVec2 textureSize, double rotationSpeed, Vec2 scale) {
        super(position, texturePath, velocityMultiplier, animationFrames,textureSize);
        this.scale=scale;
        this.rotationSpeed=rotationSpeed;
    }

    public Vehicle(Vec2 position, String texturePath, String deadTexturePath, int velocityMultiplier, int animationFrames, IVec2 textureSize, double rotationSpeed) {
        super(position, texturePath, deadTexturePath, velocityMultiplier, animationFrames,textureSize);
        this.scale=new Vec2(1,1);
        this.rotationSpeed=rotationSpeed;
    }

    public Vehicle(Vec2 position, String texturePath, String deadTexturePath, int velocityMultiplier, int animationFrames, IVec2 textureSize, double rotationSpeed, Vec2 scale) {
        super(position, texturePath, deadTexturePath, velocityMultiplier, animationFrames,textureSize);
        this.scale=scale;
        this.rotationSpeed=rotationSpeed;
    }


}
