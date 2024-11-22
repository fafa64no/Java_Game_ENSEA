package main.game;

import main.utils.vectors.BVec2;
import main.utils.vectors.IVec2;

public abstract class DynamicSprite extends Sprite{
    protected IVec2 currentVelocity;

    public DynamicSprite(IVec2 position, String texturePath) {
        super(position, texturePath);
        this.currentVelocity=new IVec2(0,0);
    }

    public DynamicSprite(IVec2 position, String texturePath, IVec2 velocity) {
        super(position, texturePath);
        this.currentVelocity = velocity;
    }

    protected void setVelocity(IVec2 vel){currentVelocity=vel;}

    public IVec2 getCurrentVelocity(){return currentVelocity;}

    public void goToNextPosition(BVec2 canMove,double friction){
        if(canMove.x) position.x+=(int)Math.round(currentVelocity.x*friction);
        if(canMove.y) position.y+=(int)Math.round(currentVelocity.y*friction);
    }
}
