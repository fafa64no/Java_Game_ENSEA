package main.physics.colliders;

import main.game.DynamicSprite;
import main.physics.Collision;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;

public class CircleCollider extends SolidCollider{
    private final double radius;

    public CircleCollider(double radius,boolean inverted, double friction, Vec2 offset, DynamicSprite parent) {
        super(inverted, friction, offset, parent);
        this.radius=radius;
    }

    private Collision circleColliderHandler(CircleCollider cc, Vec2 offset){
        BVec2 didCollide = new BVec2(cc.isInverted(), cc.isInverted());
        Vec2 previousCenterDiff = Vec2.add(cc.getOffset(),this.offset);
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
        if(Vec2.substract(cc.getOffset(), newCenterDiff).getSquareLength()<(radius-cc.getRadius())*(radius-cc.getRadius())){
            didCollide.x=!cc.isInverted();
            didCollide.y=!cc.isInverted();
        }
        if(didCollide.isFalse())return null;
        return new Collision(didCollide);
    }

    private Collision boxColliderHandler(BoxCollider bc, Vec2 offset){
        BVec2 didCollide = new BVec2(bc.isInverted(), bc.isInverted());
        Vec2 previousCenterDiff = Vec2.substract(bc.getOffset(),this.offset);
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
        Vec2 previousClosestPoint=previousCenterDiff.getClosestPoint(bc.getCenterWithoutOffset(),radius);
        Vec2 newClosestPoint=newCenterDiff.getClosestPoint(bc.getCenterWithoutOffset(),radius);
        Vec2 xPoint = new Vec2(newClosestPoint.x, previousClosestPoint.y);
        Vec2 yPoint = new Vec2(previousClosestPoint.x, newClosestPoint.y);
        if(bc.getHitbox().contains(xPoint)) didCollide.x=!bc.isInverted();
        if(bc.getHitbox().contains(yPoint)) didCollide.y=!bc.isInverted();
        if(didCollide.isFalse())return null;
        return new Collision(didCollide);
    }

    private Collision tilemapColliderHandler(TilemapCollider tc, Vec2 offset){
        return null;
    }

    @Override
    public Collision doCollide(Collider c, Vec2 offset) {
        return switch (c) {
            case BoxCollider bc -> boxColliderHandler(bc, offset);
            case CircleCollider cc -> circleColliderHandler(cc, offset);
            case TilemapCollider tc -> tilemapColliderHandler(tc, offset);
            default -> {
                System.out.println("Collider type not handled by CircleCollider");
                yield null;
            }
        };
    }

    protected double getRadius() {
        return radius;
    }
}
