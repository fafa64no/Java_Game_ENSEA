package main.physics.colliders;

import main.game.DynamicSprite;
import main.utils.vectors.Vec2;

public abstract class SolidCollider implements Collider{
    protected final boolean inverted;
    protected final double friction;
    protected Vec2 offset;
    protected final Vec2 initialOffset;
    protected final DynamicSprite parent;

    public SolidCollider(boolean inverted, double friction,Vec2 offset){
        this.inverted=inverted;
        this.friction=friction;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=null;
    }

    public SolidCollider(boolean inverted, double friction,Vec2 offset, DynamicSprite parent){
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
    public Vec2 getOffset(){
        return offset;
    }

    @Override
    public DynamicSprite getParent(){
        return parent;
    }

    @Override
    public void setOffset(DynamicSprite requester, Vec2 offset) {
        if(requester==parent){
            this.offset=Vec2.add(initialOffset,offset);
        }
    }

    @Override
    public String toString() {
        return "SolidCollider{" +
                "inverted=" + inverted +
                ", friction=" + friction +
                ", offset=" + offset +
                ", initialOffset=" + initialOffset +
                ", parent=" + parent +
                '}';
    }
}
