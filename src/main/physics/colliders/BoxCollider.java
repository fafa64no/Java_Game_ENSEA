package main.physics.colliders;

import main.game.DynamicSprite;
import main.game.characters.AIdriven;
import main.game.characters.Character;
import main.physics.ColliderType;
import main.physics.Collision;
import main.rendering.vfx.Vfx;
import main.rendering.vfx.VfxType;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

public class BoxCollider extends SolidCollider{
    private final Vec4 hitbox;
    private final Vec2 size;
    private final Vec2 centerWithoutOffset;

    public BoxCollider(Vec2 cornerA, Vec2 cornerB, boolean inverted, double friction, Vec2 offset) {
        super(inverted,friction,offset);
        double x1,x2,y1,y2;
        x1=Math.min(cornerA.x,cornerB.x);  y1=Math.min(cornerA.y,cornerB.y);
        x2=Math.max(cornerA.x,cornerB.x);  y2=Math.max(cornerA.y,cornerB.y);
        hitbox=new Vec4(x1,x2,y1,y2);
        size=new Vec2(x2-x1,y2-y1);
        centerWithoutOffset =new Vec2((x1 + x2) * 0.5,(y1 + y2) * 0.5);
    }

    public BoxCollider(Vec2 cornerA, Vec2 cornerB, boolean inverted, double friction, Vec2 offset, DynamicSprite parent, ColliderType colliderType) {
        super(inverted,friction,offset,parent,colliderType);
        double x1,x2,y1,y2;
        x1=Math.min(cornerA.x,cornerB.x);  y1=Math.min(cornerA.y,cornerB.y);
        x2=Math.max(cornerA.x,cornerB.x);  y2=Math.max(cornerA.y,cornerB.y);
        hitbox=new Vec4(x1,x2,y1,y2);
        size=new Vec2(x2-x1,y2-y1);
        centerWithoutOffset =new Vec2((x1 + x2) /2.0,(y1 + y2) /2.0);
    }

    public BoxCollider(Vec2 cornerA, Vec2 cornerB, boolean inverted, double friction, Vec2 offset, DynamicSprite parent, ColliderType colliderType, double modifier, VfxType vfxType, int vfxCooldown) {
        super(inverted,friction,offset,parent,colliderType,modifier,vfxType,vfxCooldown);
        double x1,x2,y1,y2;
        x1=Math.min(cornerA.x,cornerB.x);  y1=Math.min(cornerA.y,cornerB.y);
        x2=Math.max(cornerA.x,cornerB.x);  y2=Math.max(cornerA.y,cornerB.y);
        hitbox=new Vec4(x1,x2,y1,y2);
        size=new Vec2(x2-x1,y2-y1);
        centerWithoutOffset =new Vec2((x1 + x2) /2.0,(y1 + y2) /2.0);
    }

    public Vec4 getHitbox() {
        return hitbox;//Vec4.add(hitbox,offset);
    }

    private Collision circleColliderHandler(CircleCollider cc, Vec2 offset){
        return cc.doCollide(this,new Vec2(-offset.x,-offset.y));
    }

    private Collision boxColliderHandler(BoxCollider bc, Vec2 offset){
        BVec2 didCollide = new BVec2(bc.isInverted(),bc.isInverted());
        Vec2 previousCenterDiff = Vec2.addSubstract(centerWithoutOffset,this.offset,bc.getOffset());
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
        Vec2 xPoint = new Vec2(newCenterDiff.x, previousCenterDiff.y);
        Vec2 yPoint = new Vec2(previousCenterDiff.x, newCenterDiff.y);
        Vec4 widerHitbox = bc.getHitbox().makeItFat(Vec2.multiply(size,0.5));
        if(bc.isInverted()){
            if(widerHitbox.contains(xPoint)) didCollide.x=false;
            if(widerHitbox.contains(yPoint)) didCollide.y=false;
        }else{
            if(widerHitbox.contains(xPoint)) didCollide.x=true;
            if(widerHitbox.contains(yPoint)) didCollide.y=true;
        }
        if(didCollide.isFalse())return null;
        return new Collision(didCollide,modifier);
    }

    private Collision tilemapCollisionHandler(TilemapCollider tc, Vec2 offset){
        BVec2 didCollide = new BVec2(tc.isInverted(),tc.isInverted());
        Vec2 previousCenterDiff = Vec2.add(centerWithoutOffset,tc.getOffset(),this.offset);
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
        Vec2 xPoint = new Vec2(newCenterDiff.x, previousCenterDiff.y);
        Vec2 yPoint = new Vec2(previousCenterDiff.x, newCenterDiff.y);
        Vec4[] collisionBoxes = tc.getCollisionBoxes(this.offset);
        for (Vec4 collisionBox : collisionBoxes){
            if(collisionBox==null)continue;
            Vec4 widerHitbox = collisionBox.makeItFat(Vec2.multiply(size,0.5));
            if(tc.isInverted()){
                if(widerHitbox.contains(xPoint)) didCollide.x=false;
                if(widerHitbox.contains(yPoint)) didCollide.y=false;
            }else{
                if(widerHitbox.contains(xPoint)) didCollide.x=true;
                if(widerHitbox.contains(yPoint)) didCollide.y=true;
            }
        }
        if(didCollide.isFalse())return null;
        return new Collision(didCollide,modifier);
    }

    private Collision pointColliderHandler(PointCollider pc, Vec2 offset){
        return pc.doCollide(this,new Vec2(-offset.x,-offset.y));
    }

    @Override
    public Collision doCollide(Collider c, Vec2 offset) {
        return switch (c) {
            case BoxCollider bc -> boxColliderHandler(bc, offset);
            case CircleCollider cc -> circleColliderHandler(cc, offset);
            case TilemapCollider tc -> tilemapCollisionHandler(tc, offset);
            case PointCollider pc -> pointColliderHandler(pc, offset);
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
