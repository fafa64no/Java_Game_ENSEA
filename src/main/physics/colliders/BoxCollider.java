package main.physics.colliders;

import main.game.DynamicSprite;
import main.physics.Collision;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

public class BoxCollider extends SolidCollider{
    private final Vec2[] corners;
    private final Vec2 size;
    private final Vec2 centerWithoutOffset;

    public BoxCollider(Vec2 cornerA, Vec2 cornerB, boolean inverted, double friction, Vec2 offset) {
        super(inverted,friction,offset);
        corners=new Vec2[4];
        double x1,x2,y1,y2;
        x1=Math.min(cornerA.x,cornerB.x);  y1=Math.min(cornerA.y,cornerB.y);
        x2=Math.max(cornerA.x,cornerB.x);  y2=Math.max(cornerA.y,cornerB.y);
        corners[0]=new Vec2(x2, y2); corners[1]=new Vec2(x1, y2);
        corners[2]=new Vec2(x1, y1); corners[3]=new Vec2(x2, y1);
        size=new Vec2(x2-x1,y2-y1);
        centerWithoutOffset =new Vec2((x1 + x2) /2.0,(y1 + y2) /2.0);
    }

    public BoxCollider(Vec2 cornerA, Vec2 cornerB, boolean inverted, double friction, Vec2 offset, DynamicSprite parent) {
        super(inverted,friction,offset,parent);
        corners=new Vec2[4];
        double x1,x2,y1,y2;
        x1=Math.min(cornerA.x,cornerB.x);  y1=Math.min(cornerA.y,cornerB.y);
        x2=Math.max(cornerA.x,cornerB.x);  y2=Math.max(cornerA.y,cornerB.y);
        corners[0]=new Vec2(x2, y2); corners[1]=new Vec2(x1, y2);
        corners[2]=new Vec2(x1, y1); corners[3]=new Vec2(x2, y1);
        size=new Vec2(x2-x1,y2-y1);
        centerWithoutOffset =new Vec2((x1 + x2) /2.0,(y1 + y2) /2.0);
    }

    public Vec2[] getCorners() {return corners;}

    @Override
    public Collision doCollide(Collider c, Vec2 offset) {
        BVec2 didCollide = new BVec2(c.isInverted(),c.isInverted());
        Vec2 previousCenterDiff = Vec2.add(centerWithoutOffset,c.getOffset(),this.offset);
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
        switch (c){
            case BoxCollider bc:
                if(c.isInverted()){
                    if (
                        newCenterDiff.x<= bc.getCorners()[0].x      - size.x/2.0 &&
                        newCenterDiff.x>= bc.getCorners()[2].x      + size.x/2.0 &&
                        previousCenterDiff.y<= bc.getCorners()[0].y - size.y/2.0 &&
                        previousCenterDiff.y>= bc.getCorners()[2].y + size.y/2.0
                    ) {
                        didCollide.x=false;
                    }
                    if (
                        previousCenterDiff.x<= bc.getCorners()[0].x - size.x/2.0 &&
                        previousCenterDiff.x>= bc.getCorners()[2].x + size.x/2.0 &&
                        newCenterDiff.y<= bc.getCorners()[0].y      - size.y/2.0 &&
                        newCenterDiff.y>= bc.getCorners()[2].y      + size.y/2.0
                    ) {
                        didCollide.y=false;
                    }
                }else{
                    if (
                        newCenterDiff.x<= bc.getCorners()[0].x      + size.x/2.0 &&
                        newCenterDiff.x>= bc.getCorners()[2].x      - size.x/2.0 &&
                        previousCenterDiff.y<= bc.getCorners()[0].y + size.y/2.0 &&
                        previousCenterDiff.y>= bc.getCorners()[2].y - size.y/2.0
                    ) {
                        didCollide.x=true;
                    }
                    if (
                        previousCenterDiff.x<= bc.getCorners()[0].x + size.x/2.0 &&
                        previousCenterDiff.x>= bc.getCorners()[2].x - size.x/2.0 &&
                        newCenterDiff.y<= bc.getCorners()[0].y      + size.y/2.0 &&
                        newCenterDiff.y>= bc.getCorners()[2].y      - size.y/2.0
                    ) {
                        didCollide.y=true;
                    }
                }
                break;
            case CircleCollider cc:
                return cc.doCollide(this,new Vec2(-offset.x,-offset.y));
            case TilemapCollider tc:
                Vec4[] collsionBoxes = tc.getCollisionBoxes(this.offset);
                for (Vec4 collisionBox : collsionBoxes){
                    if(collisionBox==null)continue;
                    if(c.isInverted()){
                        if (
                            newCenterDiff.x<=       collisionBox.y + size.x/2.0 &&
                            newCenterDiff.x>=       collisionBox.x - size.x/2.0 &&
                            previousCenterDiff.y<=  collisionBox.w + size.y/2.0 &&
                            previousCenterDiff.y>=  collisionBox.z - size.y/2.0
                        ) {
                            didCollide.x=false;
                        }
                        if (
                            previousCenterDiff.x<=  collisionBox.y + size.x/2.0 &&
                            previousCenterDiff.x>=  collisionBox.x - size.x/2.0 &&
                            newCenterDiff.y<=       collisionBox.w + size.y/2.0 &&
                            newCenterDiff.y>=       collisionBox.z - size.y/2.0
                        ) {
                            didCollide.y=false;
                        }
                    }else{
                        if (
                            newCenterDiff.x<=       collisionBox.y + size.x/2.0 &&
                            newCenterDiff.x>=       collisionBox.x - size.x/2.0 &&
                            previousCenterDiff.y<=  collisionBox.w + size.y/2.0 &&
                            previousCenterDiff.y>=  collisionBox.z - size.y/2.0
                        ) {
                            didCollide.x=true;
                        }
                        if (
                            previousCenterDiff.x<=  collisionBox.y + size.x/2.0 &&
                            previousCenterDiff.x>=  collisionBox.x - size.x/2.0 &&
                            newCenterDiff.y<=       collisionBox.w + size.y/2.0 &&
                            newCenterDiff.y>=       collisionBox.z - size.y/2.0
                        ) {
                            didCollide.y=true;
                        }
                    }
                }
                break;
            default:
                System.out.println("Collider type not handled by BoxCollider");
                break;
        }
        if(didCollide.isFalse())return null;
        return new Collision(didCollide);
    }

    public Vec2 getCenterWithoutOffset() {
        return centerWithoutOffset;
    }
}
