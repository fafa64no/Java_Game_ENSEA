package physics;

import utils.vectors.BVec2;
import utils.vectors.IVec2;

public class Collider {
    private final IVec2[] corners;
    private final boolean inverted;
    private final double friction;

    public Collider() {
        this.inverted=false;
        this.corners=new IVec2[4];
        this.corners[0]=new IVec2();    this.corners[1]=new IVec2();
        this.corners[2]=new IVec2();    this.corners[3]=new IVec2();
        this.friction=0;
    }

    public Collider(int x1, int y1, int x2, int y2) {
        this.inverted=false;
        this.corners=new IVec2[4];
        this.corners[0]=new IVec2(Math.max(x1,x2),Math.max(y1,y2)); this.corners[1]=new IVec2(Math.min(x1,x2),Math.max(y1,y2));
        this.corners[2]=new IVec2(Math.min(x1,x2),Math.min(y1,y2)); this.corners[3]=new IVec2(Math.max(x1,x2),Math.min(y1,y2));
        this.friction=0.5;
    }

    public Collider(int x1, int y1, int x2, int y2, double friction) {
        this.inverted=false;
        this.corners=new IVec2[4];
        this.corners[0]=new IVec2(Math.max(x1,x2),Math.max(y1,y2)); this.corners[1]=new IVec2(Math.min(x1,x2),Math.max(y1,y2));
        this.corners[2]=new IVec2(Math.min(x1,x2),Math.min(y1,y2)); this.corners[3]=new IVec2(Math.max(x1,x2),Math.min(y1,y2));
        this.friction=friction;
    }

    public Collider(int x1, int y1, int x2, int y2, boolean inverted) {
        this.inverted=inverted;
        this.corners=new IVec2[4];
        this.corners[0]=new IVec2(Math.max(x1,x2),Math.max(y1,y2)); this.corners[1]=new IVec2(Math.min(x1,x2),Math.max(y1,y2));
        this.corners[2]=new IVec2(Math.min(x1,x2),Math.min(y1,y2)); this.corners[3]=new IVec2(Math.max(x1,x2),Math.min(y1,y2));
        this.friction=0.8;
    }

    public IVec2[] getCorners() {return corners;}

    public double getFriction() {return friction;}

    public BVec2 doCollide(Collider c, IVec2 offset1, IVec2 offset2){
        BVec2 output=new BVec2(inverted,inverted);
        for (IVec2 corner : c.getCorners()){
            IVec2 offsetedCorner1 =IVec2.add(corner,offset1);
            IVec2 offsetedCorner2 =IVec2.add(corner,offset2);
            if (
                offsetedCorner2.x<=this.corners[0].x &&
                offsetedCorner2.x>=this.corners[2].x &&
                offsetedCorner1.y<=this.corners[0].y &&
                offsetedCorner1.y>=this.corners[2].y
            ) {
                output.x=!inverted;
            }
            if (
                offsetedCorner1.x<=this.corners[0].x &&
                offsetedCorner1.x>=this.corners[2].x &&
                offsetedCorner2.y<=this.corners[0].y &&
                offsetedCorner2.y>=this.corners[2].y
            ) {
                output.y=!inverted;
            }
        }
        return output;
    }
}
