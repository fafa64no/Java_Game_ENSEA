package main.physics.colliders;

import main.game.characters.AIdriven;
import main.game.characters.Character;
import main.physics.ColliderType;
import main.physics.Collision;
import main.physics.CollisionLayer;
import main.rendering.vfx.Vfx;
import main.utils.data.Config;
import main.utils.data.DataGen;
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
            Vec2 offset
    ) {
        super(
                inverted,
                friction,
                modifier,
                colliderType,
                collisionLayer,
                offset
        );
        double x1,x2,y1,y2;
        x1=Math.min(hitBox.x,hitBox.y);  y1=Math.min(hitBox.z,hitBox.w);
        x2=Math.max(hitBox.x,hitBox.y);  y2=Math.max(hitBox.z,hitBox.w);
        this.hitBox=new Vec4(x1,x2,y1,y2);
        this.halfSizeOfHitBox =new Vec2(0.5 * (x2-x1),0.5 * (y2-y1));
        this.centerWithoutOffset =new Vec2((x1 + x2) * 0.5,(y1 + y2) * 0.5);
    }

    public Vec4 getHitBox() {
        return hitBox;
    }

    private Collision boxColliderHandler(BoxCollider bc, Vec2 offset){
        BVec2 didCollide = new BVec2(bc.inverted,bc.inverted);
        Vec2 previousCenterDiff = Vec2.addSubstract(centerWithoutOffset,this.offset,bc.getOffset());
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
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

    private Collision tileMapCollisionHandler(TilemapCollider tc, Vec2 offset){
        BVec2 didCollide = new BVec2(tc.inverted,tc.inverted);
        Vec2 previousCenterDiff = Vec2.add(centerWithoutOffset,tc.getOffset(),this.offset);
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
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

    private Collision pointColliderHandler(PointCollider pc, Vec2 offset){
        return Collision.getReverseCollision(pc.doCollide(this,offset));
    }

    @Override
    public Collision doCollide(Collider c, Vec2 relativeVelocity) {
        return switch (c) {
            case BoxCollider bc -> boxColliderHandler(bc, relativeVelocity);
            case CircleCollider cc -> circleColliderHandler(cc, relativeVelocity);
            case TilemapCollider tc -> tileMapCollisionHandler(tc, relativeVelocity);
            case PointCollider pc -> pointColliderHandler(pc, relativeVelocity);
            default -> {
                System.out.println("Collider type not handled by BoxCollider");
                yield null;
            }
        };

    }

    public Vec2 getCenterWithoutOffset() {
        return centerWithoutOffset;
    }

    @Override
    public void onCollide(ColliderType colliderType, Collision collision) {
        switch (colliderType){
            case SOLID_DAMAGE_DEALER:
            case NONE_DAMAGE_DEALER:
            case AERIAL_DAMAGE_DEALER:
                if(parent==null)break;
                if((
                        this.colliderType==ColliderType.SOLID_INERT||
                        this.colliderType==ColliderType.SOLID_INERT_ALLY||
                        this.colliderType==ColliderType.SOLID_DAMAGE_DEALER||
                        this.colliderType==ColliderType.SOLID_THICK_INERT||
                        this.colliderType==ColliderType.AERIAL_INERT
                )&& parent instanceof Character){
                    ((Character) parent).takeDamage(collision.modifier);
                }
                if(parent instanceof AIdriven){
                    ((AIdriven) parent).startAI();
                }
                break;
            case SOLID_INERT_ALLY:
                if(this.colliderType==ColliderType.NONE_TRIGGER){
                    if(parent instanceof AIdriven){
                        ((AIdriven) parent).startAI();
                    }
                }
                break;
            default:
                break;
        }

        switch (vfxType){
            case VFX_ELECTRICITY:
                new Vfx(offset, Config.largeTileSize, DataGen.getElectricTextures(),7);
                break;
            default:
                break;
        }
    }
}
