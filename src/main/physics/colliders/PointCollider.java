package main.physics.colliders;

import main.physics.ColliderType;
import main.physics.Collision;
import main.physics.layers.CollisionLayer;
import main.physics.dynamic_objects.DynamicPoint;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

public class PointCollider extends Collider{

    public PointCollider(
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
    }

    @Override
    protected Collision boxColliderHandler(BoxCollider bc, Vec2 relativeVelocity){
        BVec2 didCollide = new BVec2(bc.inverted,bc.inverted);
        Vec2 previousCenterDiff = Vec2.substract(this.offset,bc.getOffset());
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,relativeVelocity);
        Vec2 xPoint = new Vec2(newCenterDiff.x, previousCenterDiff.y);
        Vec2 yPoint = new Vec2(previousCenterDiff.x, newCenterDiff.y);
        Vec4 hitBox = bc.getHitBox();
        if(bc.inverted){
            if(hitBox.contains(xPoint)) didCollide.x=false;
            if(hitBox.contains(yPoint)) didCollide.y=false;
        }else{
            if(hitBox.contains(xPoint)) didCollide.x=true;
            if(hitBox.contains(yPoint)) didCollide.y=true;
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
        Vec2 previousCenterDiff = Vec2.substract(this.offset,tc.getOffset());
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,relativeVelocity);
        Vec2 xPoint = new Vec2(newCenterDiff.x, previousCenterDiff.y);
        Vec2 yPoint = new Vec2(previousCenterDiff.x, newCenterDiff.y);
        Vec4[] collisionBoxes = tc.getCollisionBoxes(this.offset);
        for (Vec4 collisionBox : collisionBoxes){
            if(collisionBox==null)continue;
            if(tc.inverted){
                if(collisionBox.contains(xPoint)) didCollide.x=false;
                if(collisionBox.contains(yPoint)) didCollide.y=false;
            }else{
                if(collisionBox.contains(xPoint)) didCollide.x=true;
                if(collisionBox.contains(yPoint)) didCollide.y=true;
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
    protected Collision pointColliderHandler(PointCollider pc, Vec2 relativeVelocity) {
        return null;
    }
}
