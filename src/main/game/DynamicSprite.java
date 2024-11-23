package main.game;

import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;

public abstract class DynamicSprite extends Sprite{
    protected Vec2 currentVelocity;

    public DynamicSprite(Vec2 position, String texturePath) {
        super(position, texturePath);
        this.currentVelocity=new Vec2();
    }

    public DynamicSprite(Vec2 position, String texturePath, Vec2 velocity) {
        super(position, texturePath);
        this.currentVelocity = velocity;
    }

    protected void setVelocity(Vec2 vel){currentVelocity=vel;}

    public Vec2 getCurrentVelocity(){return currentVelocity;}

    public void goToNextPosition(BVec2 canMove,double friction){
        if(canMove.x) position.x+=currentVelocity.x*friction;
        if(canMove.y) position.y+=currentVelocity.y*friction;
    }
}
