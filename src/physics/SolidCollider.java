package physics;

import game.DynamicSprite;
import utils.vectors.IVec2;

public abstract class SolidCollider implements Collider{
    protected final boolean inverted;
    protected final double friction;
    private IVec2 offset;
    private final IVec2 initialOffset;
    private final DynamicSprite parent;

    public SolidCollider(boolean inverted, double friction,IVec2 offset){
        this.inverted=inverted;
        this.friction=friction;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=null;
    }

    public SolidCollider(boolean inverted, double friction,IVec2 offset, DynamicSprite parent){
        this.inverted=inverted;
        this.friction=friction;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=parent;
    }

    public boolean isInverted() {return inverted;}

    public double getFriction() {return friction;}

    @Override
    public void onCollide() {

    }

    @Override
    public IVec2 getOffset(){
        return offset;
    }

    @Override
    public DynamicSprite getParent(){
        return parent;
    }


}
