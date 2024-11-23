package main.physics.colliders;

import main.game.DynamicSprite;
import main.physics.Collision;
import main.utils.vectors.BVec2;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

public class CircleCollider extends SolidCollider{
    private final double radius;

    public CircleCollider(double radius,boolean inverted, double friction, IVec2 offset, DynamicSprite parent) {
        super(inverted, friction, offset, parent);
        this.radius=radius;
    }

    @Override
    public Collision doCollide(Collider c, IVec2 offset) {
        BVec2 didCollide = new BVec2(c.isInverted(), c.isInverted());
        IVec2 previousCenterDiff = IVec2.add(c.getOffset(),this.offset);
        IVec2 newCenterDiff = IVec2.add(previousCenterDiff,offset);
        switch (c){
            case BoxCollider bc:
                Vec2 previousClosestPoint=previousCenterDiff.getClosestPoint(bc.getCenterWithoutOffset(),radius);
                Vec2 newClosestPoint=newCenterDiff.getClosestPoint(bc.getCenterWithoutOffset(),radius);
                if (
                    newClosestPoint.x<= bc.getCorners()[0].x &&
                    newClosestPoint.x>= bc.getCorners()[2].x &&
                    previousClosestPoint.y<= bc.getCorners()[0].y &&
                    previousClosestPoint.y>= bc.getCorners()[2].y
                ) {
                    didCollide.x=!c.isInverted();
                }
                if (
                    previousClosestPoint.x<= bc.getCorners()[0].x &&
                    previousClosestPoint.x>= bc.getCorners()[2].x &&
                    newClosestPoint.y<= bc.getCorners()[0].y &&
                    newClosestPoint.y>= bc.getCorners()[2].y
                ) {
                    didCollide.y=!c.isInverted();
                }
                break;
            case CircleCollider cc:
                if(IVec2.substract(c.getOffset(), newCenterDiff).getSquareLength()<(radius-cc.getRadius())*(radius-cc.getRadius())){
                    didCollide.x=!c.isInverted();
                    didCollide.y=!c.isInverted();
                }
                break;
            default:
                System.out.println("Collider type not handled by CircleCollider");
                break;
        }
        if(didCollide.isFalse())return null;
        return new Collision(didCollide);
    }

    protected double getRadius() {
        return radius;
    }
}
