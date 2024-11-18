package game;

import physics.Collider;
import utils.BVec2;
import utils.IVec2;

public abstract class DynamicSprite extends Sprite{
    protected IVec2 currentVelocity;
    private final IVec2 initialPosition;
    protected IVec2 nextPosition=new IVec2();

    public DynamicSprite(IVec2 position, Collider collider, String texturePath) {
        super(position, collider, texturePath);
        this.currentVelocity=new IVec2(0,0);
        this.initialPosition=position;
    }

    public DynamicSprite(IVec2 position, Collider collider, String texturePath, IVec2 velocity) {
        super(position, collider, texturePath);
        this.currentVelocity = velocity;
        this.initialPosition=position;
    }

    protected void setVelocity(IVec2 vel){
        this.currentVelocity=vel;
    }

    public IVec2 computeNewPosition(){
        this.nextPosition=IVec2.add(this.position,this.currentVelocity);
        return this.nextPosition;
    }

    public void goToNextPosition(BVec2 canMove,double friction){
        if(canMove.x) this.position.x+=(int)Math.round((this.nextPosition.x-this.position.x)*friction);
        if(canMove.y) this.position.y+=(int)Math.round((this.nextPosition.y-this.position.y)*friction);
    }

    public void resetPosition() { super.position=this.initialPosition; }

}
