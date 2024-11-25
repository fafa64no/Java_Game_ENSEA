package main.physics.colliders;

import main.physics.ColliderType;
import main.physics.Collision;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

public class PointCollider extends SolidCollider{
    public PointCollider(Vec2 offset, ColliderType colliderType) {
        super(false, 1, offset, null, colliderType);
    }

    private Collision boxColliderHandler(BoxCollider bc, Vec2 offset){
        BVec2 didCollide = new BVec2(bc.isInverted(),bc.isInverted());
        Vec2 previousCenterDiff = Vec2.substract(this.offset,bc.getOffset());
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
        Vec2 xPoint = new Vec2(newCenterDiff.x, previousCenterDiff.y);
        Vec2 yPoint = new Vec2(previousCenterDiff.x, newCenterDiff.y);
        Vec4 hitbox = bc.getHitbox();
        if(!bc.isInverted()){
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
                System.out.println("Collider type not handled by BoxCollider");
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
}
