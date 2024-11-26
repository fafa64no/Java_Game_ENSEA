package main.physics.colliders;

import main.game.DynamicSprite;
import main.game.projectiles.Projectile;
import main.physics.ColliderType;
import main.physics.Collision;
import main.utils.data.Config;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

public class PointCollider implements Collider{
    protected final boolean inverted;
    protected final double friction;
    protected Vec2 offset;
    protected final Vec2 initialOffset;
    protected final Projectile parent;
    protected final ColliderType colliderType;

    public PointCollider(Vec2 offset, ColliderType colliderType) {
        this.inverted=false;
        this.friction=1;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=null;
        this.colliderType=colliderType;
    }

    public PointCollider(Vec2 offset, ColliderType colliderType, Projectile parent) {
        this.inverted=false;
        this.friction=1;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=parent;
        this.colliderType=colliderType;
    }

    private Collision boxColliderHandler(BoxCollider bc, Vec2 offset){
        BVec2 didCollide = new BVec2(bc.isInverted(),bc.isInverted());
        Vec2 previousCenterDiff = Vec2.substract(this.offset,bc.getOffset());
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
        Vec2 xPoint = new Vec2(newCenterDiff.x, previousCenterDiff.y);
        Vec2 yPoint = new Vec2(previousCenterDiff.x, newCenterDiff.y);
        Vec4 hitbox = bc.getHitbox();
        if(Config.currentlyDebugging&&bc.getOffset().x>-170&&bc.getOffset().y>-160&&bc.getOffset().x<-160&&bc.getOffset().y<-150){
            System.out.println("xPoint = "+xPoint+"\nyPoint = "+yPoint+"\nhitbox = "+hitbox+"\nbc.getOffset() = "+bc.getOffset()+"\nthis.getOffset() = "+this.getOffset());
            Config.currentlyDebugging=false;
        }
        if(bc.isInverted()){
            if(hitbox.contains(xPoint)) didCollide.x=false;
            if(hitbox.contains(yPoint)) didCollide.y=false;
        }else{
            if(hitbox.contains(xPoint)) didCollide.x=true;
            if(hitbox.contains(yPoint)) didCollide.y=true;
        }
        if(didCollide.isFalse())return null;
        return new Collision(didCollide);
    }

    @Override
    public Collision doCollide(Collider c, Vec2 offset) {
        return switch (c) {
            case BoxCollider bc -> boxColliderHandler(bc, offset);
            default -> {
                System.out.println("Collider type not handled by PointCollider");
                yield null;
            }
        };
    }

    @Override
    public void onCollide(ColliderType colliderType) {
        switch (colliderType){
            case SOLID_DAMAGE_DEALER:
            case SOLID_INERT:
                if(parent==null)break;
                break;
            default:
                break;
        }
    }

    @Override
    public Vec2 getOffset() {
        return offset;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    @Override
    public DynamicSprite getParent() {
        return null;
    }

    @Override
    public double getFriction() {
        return friction;
    }

    @Override
    public void setOffset() {
        if(parent==null)return;
        this.offset=Vec2.add(initialOffset,parent.getCurrentPosition());
    }

    @Override
    public ColliderType getColliderType() {
        return colliderType;
    }
}
