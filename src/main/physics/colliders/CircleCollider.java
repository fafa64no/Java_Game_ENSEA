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

    @Override
    public Collision doCollide(Collider c, Vec2 offset) {
        BVec2 didCollide = new BVec2(c.isInverted(), c.isInverted());
        Vec2 previousCenterDiff = Vec2.add(c.getOffset(),this.offset);
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
        Vec2 previousClosestPoint=new Vec2();
        Vec2 newClosestPoint=new Vec2();
        switch (c){
            case BoxCollider bc:
                previousClosestPoint=previousCenterDiff.getClosestPoint(bc.getCenterWithoutOffset(),radius);
                newClosestPoint=newCenterDiff.getClosestPoint(bc.getCenterWithoutOffset(),radius);
                if (
                    newClosestPoint.x<= bc.getCorners()[0].x &&
                    newClosestPoint.x>= bc.getCorners()[2].x &&
                    previousClosestPoint.y<= bc.getCorners()[0].y &&
                    previousClosestPoint.y>= bc.getCorners()[2].y
                ) {
                    didCollide.x=!bc.isInverted();
                }
                if (
                    previousClosestPoint.x<= bc.getCorners()[0].x &&
                    previousClosestPoint.x>= bc.getCorners()[2].x &&
                    newClosestPoint.y<= bc.getCorners()[0].y &&
                    newClosestPoint.y>= bc.getCorners()[2].y
                ) {
                    didCollide.y=!bc.isInverted();
                }
                break;
            case CircleCollider cc:
                if(Vec2.substract(c.getOffset(), newCenterDiff).getSquareLength()<(radius-cc.getRadius())*(radius-cc.getRadius())){
                    didCollide.x=!c.isInverted();
                    didCollide.y=!c.isInverted();
                }
                break;
            default:
                System.out.println("Collider type not handled by CircleCollider");
                break;
        }
        if(didCollide.isFalse())return null;
        //System.out.println("-------------\npreviousCenterDiff : "+previousCenterDiff.x+" : "+previousCenterDiff.y+
        //        "\npreviousClosestPoint : "+previousClosestPoint.x+" : "+previousClosestPoint.y+
        //        "\nnewCenterDiff : "+newCenterDiff.x+" : "+newCenterDiff.y+
        //        "\nnewClosestPoint : "+newClosestPoint.x+" : "+newClosestPoint.y);
        return new Collision(didCollide);
    }

    protected double getRadius() {
        return radius;
    }
}
