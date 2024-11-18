package game.characters;

import game.DynamicSprite;
import physics.Collider;
import utils.IVec2;

public abstract class Character extends DynamicSprite {
    private final int velocityMultiplier;

    protected IVec2 currentDir = new IVec2();
    protected final IVec2 previousDir = new IVec2();
    protected final IVec2 textureSize;

    protected int animationCounter;
    protected final int animationFrames;
    protected final int animationSpeed=4;

    public Character(IVec2 position, Collider collider, String texturePath, int velocityMultiplier, int animationFrames, IVec2 textureSize) {
        super(position, collider, texturePath);
        this.velocityMultiplier = velocityMultiplier;
        this.animationFrames=animationFrames;
        this.textureSize=textureSize;
    }

    public void setInput(IVec2 vel) {
        super.setVelocity(IVec2.multiply(vel,this.velocityMultiplier));
        if (this.currentDir.x!=0||this.currentDir.y!=0){
            this.previousDir.x=this.currentDir.x;
            this.previousDir.y=this.currentDir.y;
        }
        this.currentDir=vel;
    }
}
