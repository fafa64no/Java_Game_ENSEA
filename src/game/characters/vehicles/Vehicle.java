package game.characters.vehicles;

import game.characters.Character;
import physics.Collider;
import utils.vectors.IVec2;

public abstract class Vehicle extends Character {
    protected IVec2 scale;
    protected double rotation=0;
    protected final double rotationSpeed;
    public Vehicle(IVec2 position, Collider collider, String texturePath, int velocityMultiplier, int animationFrames, IVec2 textureSize, double rotationSpeed) {
        super(position, collider, texturePath, velocityMultiplier, animationFrames,textureSize);
        this.scale=new IVec2(1,1);
        this.rotationSpeed=rotationSpeed;
    }


}
