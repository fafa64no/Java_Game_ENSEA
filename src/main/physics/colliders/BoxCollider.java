package main.physics.colliders;

import main.physics.ColliderType;
import main.physics.Collision;
import main.physics.layers.CollisionLayer;
import main.physics.dynamic_objects.DynamicPoint;
import main.rendering.vfx.VfxType;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

public class BoxCollider extends Collider{
    protected final Vec4 hitBox;
    protected final Vec2 halfSizeOfHitBox;
    protected final Vec2 centerWithoutOffset;

    public BoxCollider(
            Vec4 hitBox,
            boolean inverted,
            double friction,
            double modifier,
            ColliderType colliderType,
            CollisionLayer collisionLayer,
            Vec2 offset,
            DynamicPoint parent
    ) {
        super(
                inverted,
                friction,
                modifier,
                colliderType,
                collisionLayer,
                offset,
                parent
        );
        double x1,x2,y1,y2;
        x1=Math.min(hitBox.x,hitBox.y);  y1=Math.min(hitBox.z,hitBox.w);
        x2=Math.max(hitBox.x,hitBox.y);  y2=Math.max(hitBox.z,hitBox.w);
        this.hitBox=new Vec4(x1,x2,y1,y2);
        this.halfSizeOfHitBox =new Vec2(0.5 * (x2-x1),0.5 * (y2-y1));
        this.centerWithoutOffset =new Vec2((x1 + x2) * 0.5,(y1 + y2) * 0.5);
    }

    public BoxCollider(
            Vec4 hitBox,
            boolean inverted,
            double friction,
            double modifier,
            ColliderType colliderType,
            CollisionLayer collisionLayer,
            Vec2 offset,
            VfxType vfxType,
            int vfxCooldown,
            int vfxDuration,
            DynamicPoint parent
    ) {
        super(
                inverted,
                friction,
                modifier,
                colliderType,
                collisionLayer,
                offset,
                vfxType,
                vfxCooldown,
                vfxDuration,
                parent
        );
        double x1,x2,y1,y2;
        x1=Math.min(hitBox.x,hitBox.y);  y1=Math.min(hitBox.z,hitBox.w);
        x2=Math.max(hitBox.x,hitBox.y);  y2=Math.max(hitBox.z,hitBox.w);
        this.hitBox=new Vec4(x1,x2,y1,y2);
        this.halfSizeOfHitBox =new Vec2(0.5 * (x2-x1),0.5 * (y2-y1));
        this.centerWithoutOffset =new Vec2((x1 + x2) * 0.5,(y1 + y2) * 0.5);
    }

    @Override
    protected Collision boxColliderHandler(BoxCollider bc, Vec2 relativeVelocity){
        BVec2 didCollide = new BVec2(bc.inverted,bc.inverted);
        Vec2 previousCenterDiff = Vec2.addSubstract(centerWithoutOffset,this.offset,bc.getOffset());
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,relativeVelocity);
        Vec2 xPoint = new Vec2(newCenterDiff.x, previousCenterDiff.y);
        Vec2 yPoint = new Vec2(previousCenterDiff.x, newCenterDiff.y);
        Vec4 widerHitBox = bc.getHitBox().makeItFat(halfSizeOfHitBox);
        if(bc.inverted){
            if(widerHitBox.contains(xPoint)) didCollide.x=false;
            if(widerHitBox.contains(yPoint)) didCollide.y=false;
        }else{
            if(widerHitBox.contains(xPoint)) didCollide.x=true;
            if(widerHitBox.contains(yPoint)) didCollide.y=true;
        }
        if(didCollide.isFalse())return null;
        return new Collision(
                this,
                bc,
                this.colliderType,
                bc.colliderType,
                this.collisionLayer,
                bc.collisionLayer,
                this.friction,
                bc.friction,
                this.modifier,
                bc.modifier,
                didCollide,
                new Vec2()
        );
    }

    @Override
    protected Collision tileMapColliderHandler(TileMapCollider tc, Vec2 relativeVelocity){
        BVec2 didCollide = new BVec2(tc.inverted,tc.inverted);
        Vec2 previousCenterDiff = Vec2.add(centerWithoutOffset,tc.getOffset(),this.offset);
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,relativeVelocity);
        Vec2 xPoint = new Vec2(newCenterDiff.x, previousCenterDiff.y);
        Vec2 yPoint = new Vec2(previousCenterDiff.x, newCenterDiff.y);
        Vec4[] collisionBoxes = tc.getCollisionBoxes(this.offset);
        for (Vec4 collisionBox : collisionBoxes){
            if(collisionBox==null)continue;
            Vec4 widerHitBox = collisionBox.makeItFat(halfSizeOfHitBox);
            if(tc.inverted){
                if(widerHitBox.contains(xPoint)) didCollide.x=false;
                if(widerHitBox.contains(yPoint)) didCollide.y=false;
            }else{
                if(widerHitBox.contains(xPoint)) didCollide.x=true;
                if(widerHitBox.contains(yPoint)) didCollide.y=true;
            }
        }
        if(didCollide.isFalse())return null;
        return new Collision(
                this,
                tc,
                this.colliderType,
                tc.colliderType,
                this.collisionLayer,
                tc.collisionLayer,
                this.friction,
                tc.friction,
                this.modifier,
                tc.modifier,
                didCollide,
                new Vec2()
        );
    }

    @Override
    protected Collision pointColliderHandler(PointCollider pc, Vec2 relativeVelocity){
        return Collision.getReverseCollision(pc.doCollide(this,relativeVelocity));
    }

    public Vec4 getHitBox() {
        return hitBox;
    }

    public Vec2 getCenterWithoutOffset() {
        return centerWithoutOffset;
    }
}
