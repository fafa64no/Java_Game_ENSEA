package physics;

import game.DynamicSprite;
import utils.vectors.BVec2;
import utils.vectors.IVec2;

public class BoxCollider extends SolidCollider{
    private final IVec2[] corners;

    public BoxCollider(IVec2 cornerA, IVec2 cornerB, boolean inverted, double friction, IVec2 offset) {
        super(inverted,friction,offset);
        corners=new IVec2[4];
        int x1,x2,y1,y2;
        x1=Math.min(cornerA.x,cornerB.x);  y1=Math.min(cornerA.y,cornerB.y);
        x2=Math.max(cornerA.x,cornerB.x);  y2=Math.max(cornerA.y,cornerB.y);
        corners[0]=new IVec2(x2, y2); corners[1]=new IVec2(x1, y2);
        corners[2]=new IVec2(x1, y1); corners[3]=new IVec2(x2, y1);
    }

    public BoxCollider(IVec2 cornerA, IVec2 cornerB, boolean inverted, double friction, IVec2 offset, DynamicSprite parent) {
        super(inverted,friction,offset,parent);
        corners=new IVec2[4];
        int x1,x2,y1,y2;
        x1=Math.min(cornerA.x,cornerB.x);  y1=Math.min(cornerA.y,cornerB.y);
        x2=Math.max(cornerA.x,cornerB.x);  y2=Math.max(cornerA.y,cornerB.y);
        corners[0]=new IVec2(x2, y2); corners[1]=new IVec2(x1, y2);
        corners[2]=new IVec2(x1, y1); corners[3]=new IVec2(x2, y1);
    }

    public IVec2[] getCorners() {return corners;}

    @Override
    public BVec2 doCollide(Collider c, IVec2 offset) {
        BVec2 output=new BVec2(c.isInverted(),c.isInverted());
        for (IVec2 cornerA : this.corners){
            IVec2 offsetedCorner1 = IVec2.add(cornerA,c.getOffset());
            IVec2 offsetedCorner2 = IVec2.add(cornerA,c.getOffset(),offset);
            switch (c){
                case BoxCollider sc:
                    if (
                        offsetedCorner2.x<=sc.getCorners()[0].x &&
                        offsetedCorner2.x>=sc.getCorners()[2].x &&
                        offsetedCorner1.y<=sc.getCorners()[0].y &&
                        offsetedCorner1.y>=sc.getCorners()[2].y
                    ) {
                        output.x=!c.isInverted();
                    }
                    if (
                        offsetedCorner1.x<=sc.getCorners()[0].x &&
                        offsetedCorner1.x>=sc.getCorners()[2].x &&
                        offsetedCorner2.y<=sc.getCorners()[0].y &&
                        offsetedCorner2.y>=sc.getCorners()[2].y
                    ) {
                        output.y=!c.isInverted();
                    }
                    break;
                default:
                    System.out.println("Collider type not handled");
                    break;
            }
        }
        return output;
    }
}
