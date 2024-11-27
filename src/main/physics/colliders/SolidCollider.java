package main.physics.colliders;

import main.game.DynamicSprite;
import main.physics.ColliderType;
import main.physics.Collision;
import main.rendering.vfx.VfxType;
import main.utils.vectors.Vec2;

public abstract class SolidCollider implements Collider{
    protected final boolean inverted;
    protected final double friction;
    protected Vec2 offset;
    protected final Vec2 initialOffset;
    protected final DynamicSprite parent;
    protected final ColliderType colliderType;
    protected final double modifier;

    protected final VfxType vfxType;
    protected final int vfxCooldown;
    protected int remainingVfxDelay = 0;

    public SolidCollider(boolean inverted, double friction,Vec2 offset){
        this.inverted=inverted;
        this.friction=friction;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=null;
        this.colliderType=ColliderType.SOLID_INERT;
        this.modifier=0;
        this.vfxType=VfxType.VFX_NONE;
        this.vfxCooldown=-1;
    }

    public SolidCollider(boolean inverted, double friction,Vec2 offset, DynamicSprite parent){
        this.inverted=inverted;
        this.friction=friction;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=parent;
        this.colliderType=ColliderType.SOLID_INERT;
        this.modifier=0;
        this.vfxType=VfxType.VFX_NONE;
        this.vfxCooldown=-1;
    }

    public SolidCollider(boolean inverted, double friction,Vec2 offset, DynamicSprite parent, ColliderType colliderType){
        this.inverted=inverted;
        this.friction=friction;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=parent;
        this.colliderType=colliderType;
        this.modifier=0;
        this.vfxType=VfxType.VFX_NONE;
        this.vfxCooldown=-1;
    }

    public SolidCollider(boolean inverted, double friction,Vec2 offset, DynamicSprite parent, ColliderType colliderType, double modifier){
        this.inverted=inverted;
        this.friction=friction;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=parent;
        this.colliderType=colliderType;
        this.modifier=modifier;
        this.vfxType=VfxType.VFX_NONE;
        this.vfxCooldown=-1;
    }

    public SolidCollider(boolean inverted, double friction,Vec2 offset, DynamicSprite parent, ColliderType colliderType, double modifier, VfxType vfxType, int vfxCooldown){
        this.inverted=inverted;
        this.friction=friction;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=parent;
        this.colliderType=colliderType;
        this.modifier=modifier;
        this.vfxType=vfxType;
        this.vfxCooldown=vfxCooldown;
    }

    public boolean isInverted() {return inverted;}

    public double getFriction() {return friction;}

    @Override
    public void onCollide(ColliderType colliderType, Collision collision) {

    }

    @Override
    public Collision getReverseCollision(Collision collision) {
        return new Collision(collision.collisions,modifier);
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
    public void setOffset() {
        if(parent==null)return;
        this.offset=Vec2.add(initialOffset,parent.getPosition());
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

    @Override
    public ColliderType getColliderType() {
        return colliderType;
    }
}
