package main.physics.colliders;

import main.game.DynamicSprite;
import main.utils.vectors.BVec2;
import main.utils.vectors.IVec2;

public class CircleCollider extends SolidCollider{
    private final double radius;

    public CircleCollider(double radius,boolean inverted, double friction, IVec2 offset, DynamicSprite parent) {
        super(inverted, friction, offset, parent);
        this.radius=radius;
    }

    @Override
    public BVec2 doCollide(Collider c, IVec2 offset) {
        BVec2 output=new BVec2(c.isInverted(), c.isInverted());
        IVec2 previousCenterDiff =IVec2.add(c.getOffset(),this.offset);
        IVec2 newCenterDiff =IVec2.add(c.getOffset(),this.offset,offset);
        switch (c){
            case BoxCollider bc:
                if (
                    newCenterDiff.x<= bc.getCorners()[0].x + radius/2 &&
                    newCenterDiff.x>= bc.getCorners()[2].x - radius/2 &&
                    previousCenterDiff.y<= bc.getCorners()[0].y + radius/2 &&
                    previousCenterDiff.y>= bc.getCorners()[2].y - radius/2
                ) {
                    output.x=!c.isInverted();
                }
                if (
                    previousCenterDiff.x<= bc.getCorners()[0].x + radius/2 &&
                    previousCenterDiff.x>= bc.getCorners()[2].x - radius/2 &&
                    newCenterDiff.y<= bc.getCorners()[0].y + radius/2 &&
                    newCenterDiff.y>= bc.getCorners()[2].y - radius/2
                ) {
                    output.y=!c.isInverted();
                }
                break;
            case CircleCollider cc:
                if(IVec2.substract(c.getOffset(), newCenterDiff).getSquareLength()<(radius-cc.getRadius())*(radius-cc.getRadius())){
                    output.x=!c.isInverted();
                    output.y=!c.isInverted();
                }
                break;
            default:
                System.out.println("Collider type not handled by CircleCollider");
                break;
        }
        return output;
    }

    protected double getRadius() {
        return radius;
    }
}
