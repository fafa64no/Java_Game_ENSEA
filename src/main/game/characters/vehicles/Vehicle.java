package main.game.characters.vehicles;

import main.game.characters.Character;
import main.game.characters.Target;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

public abstract class Vehicle extends Character implements Target {
    protected Vec2 scale;
    protected double rotation=0;
    protected final double rotationSpeed;

    public Vehicle(Vec2 position, String texturePath, String deadTexturePath, int velocityMultiplier, IVec2 textureSize, double rotationSpeed) {
        super(position, texturePath, deadTexturePath, velocityMultiplier,textureSize);
        this.scale=new Vec2(1,1);
        this.rotationSpeed=rotationSpeed;
    }

    public Vehicle(Vec2 position, String texturePath, String deadTexturePath, int velocityMultiplier, IVec2 textureSize, double rotationSpeed, double maxHealth) {
        super(position, texturePath, deadTexturePath, velocityMultiplier,textureSize,maxHealth);
        this.scale=new Vec2(1,1);
        this.rotationSpeed=rotationSpeed;
    }
}
