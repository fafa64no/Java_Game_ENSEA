package main.physics.colliders;

import main.game.DynamicSprite;
import main.physics.Collision;
import main.utils.vectors.BVec2;
import main.utils.vectors.IVec2;

public class BoxCollider extends SolidCollider{
    private final IVec2[] corners;
    private final IVec2 size;
    private final IVec2 centerWithoutOffset;

    public BoxCollider(IVec2 cornerA, IVec2 cornerB, boolean inverted, double friction, IVec2 offset) {
        super(inverted,friction,offset);
        corners=new IVec2[4];
        int x1,x2,y1,y2;
        x1=Math.min(cornerA.x,cornerB.x);  y1=Math.min(cornerA.y,cornerB.y);
        x2=Math.max(cornerA.x,cornerB.x);  y2=Math.max(cornerA.y,cornerB.y);
        corners[0]=new IVec2(x2, y2); corners[1]=new IVec2(x1, y2);
        corners[2]=new IVec2(x1, y1); corners[3]=new IVec2(x2, y1);
        size=new IVec2(x2-x1,y2-y1);
        centerWithoutOffset =new IVec2(Math.round((float) (x1 + x2) /2),Math.round((float) (y1 + y2) /2));
    }

    public BoxCollider(IVec2 cornerA, IVec2 cornerB, boolean inverted, double friction, IVec2 offset, DynamicSprite parent) {
        super(inverted,friction,offset,parent);
        corners=new IVec2[4];
        int x1,x2,y1,y2;
        x1=Math.min(cornerA.x,cornerB.x);  y1=Math.min(cornerA.y,cornerB.y);
        x2=Math.max(cornerA.x,cornerB.x);  y2=Math.max(cornerA.y,cornerB.y);
        corners[0]=new IVec2(x2, y2); corners[1]=new IVec2(x1, y2);
        corners[2]=new IVec2(x1, y1); corners[3]=new IVec2(x2, y1);
        size=new IVec2(x2-x1,y2-y1);
        centerWithoutOffset =new IVec2(Math.round((float) (x1 + x2) /2),Math.round((float) (y1 + y2) /2));
    }

    public IVec2[] getCorners() {return corners;}

    @Override
    public Collision doCollide(Collider c, IVec2 offset) {
        BVec2 didCollide = new BVec2(c.isInverted(),c.isInverted());
        IVec2 previousCenterDiff = IVec2.add(centerWithoutOffset,c.getOffset(),this.offset);
        IVec2 newCenterDiff = IVec2.add(previousCenterDiff,offset);
        switch (c){
            case BoxCollider bc:
                if (
                    newCenterDiff.x<= bc.getCorners()[0].x + size.x/2 &&
                    newCenterDiff.x>= bc.getCorners()[2].x - size.x/2 &&
                    previousCenterDiff.y<= bc.getCorners()[0].y + size.y/2 &&
                    previousCenterDiff.y>= bc.getCorners()[2].y - size.y/2
                ) {
                    didCollide.x=!c.isInverted();
                }
                if (
                    previousCenterDiff.x<= bc.getCorners()[0].x + size.x/2 &&
                    previousCenterDiff.x>= bc.getCorners()[2].x - size.x/2 &&
                    newCenterDiff.y<= bc.getCorners()[0].y + size.y/2 &&
                    newCenterDiff.y>= bc.getCorners()[2].y - size.y/2
                ) {
                    didCollide.y=!c.isInverted();
                }
                break;
            case CircleCollider cc:
                return cc.doCollide(this,new IVec2(-offset.x,-offset.y));
            default:
                System.out.println("Collider type not handled by BoxCollider");
                break;
        }
        if(didCollide.isFalse())return null;
        return new Collision(didCollide);
    }

    public IVec2 getCenterWithoutOffset() {
        return centerWithoutOffset;
    }
}
