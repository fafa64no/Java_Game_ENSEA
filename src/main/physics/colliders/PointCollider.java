package main.physics.colliders;

import main.game.DynamicSprite;
import main.game.projectiles.Projectile;
import main.physics.ColliderType;
import main.physics.Collision;
import main.rendering.vfx.Vfx;
import main.rendering.vfx.VfxType;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

public class PointCollider implements Collider{
    protected final boolean inverted;
    protected final double friction;
    protected Vec2 offset;
    protected final Vec2 initialOffset;
    protected final Projectile parent;
    protected final ColliderType colliderType;
    protected final double modifier;

    protected final VfxType vfxType;

    public PointCollider(Vec2 offset, ColliderType colliderType, Projectile parent, double modifier) {
        this.inverted=false;
        this.friction=1;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=parent;
        this.colliderType=colliderType;
        this.modifier=modifier;
        this.vfxType=VfxType.VFX_NONE;
    }

    public PointCollider(Vec2 offset, ColliderType colliderType, Projectile parent, double modifier, VfxType vfxType) {
        this.inverted=false;
        this.friction=1;
        this.offset=offset;
        this.initialOffset=offset;
        this.parent=parent;
        this.colliderType=colliderType;
        this.modifier=modifier;
        this.vfxType=vfxType;
    }

    private Collision boxColliderHandler(BoxCollider bc, Vec2 offset){
        BVec2 didCollide = new BVec2(bc.isInverted(),bc.isInverted());
        Vec2 previousCenterDiff = Vec2.substract(this.offset,bc.getOffset());
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
        Vec2 xPoint = new Vec2(newCenterDiff.x, previousCenterDiff.y);
        Vec2 yPoint = new Vec2(previousCenterDiff.x, newCenterDiff.y);
        Vec4 hitbox = bc.getHitbox();
        if(bc.isInverted()){
            if(hitbox.contains(xPoint)) didCollide.x=false;
            if(hitbox.contains(yPoint)) didCollide.y=false;
        }else{
            if(hitbox.contains(xPoint)) didCollide.x=true;
            if(hitbox.contains(yPoint)) didCollide.y=true;
        }
        if(didCollide.isFalse())return null;
        return new Collision(didCollide,modifier);
    }

    private Collision tilemapCollisionHandler(TilemapCollider tc, Vec2 offset){
        BVec2 didCollide = new BVec2(tc.isInverted(),tc.isInverted());
        Vec2 previousCenterDiff = Vec2.substract(this.offset,tc.getOffset());
        Vec2 newCenterDiff = Vec2.add(previousCenterDiff,offset);
        Vec2 xPoint = new Vec2(newCenterDiff.x, previousCenterDiff.y);
        Vec2 yPoint = new Vec2(previousCenterDiff.x, newCenterDiff.y);
        Vec4[] collisionBoxes = tc.getCollisionBoxes(this.offset);
        for (Vec4 collisionBox : collisionBoxes){
            if(collisionBox==null)continue;
            if(tc.isInverted()){
                if(collisionBox.contains(xPoint)) didCollide.x=false;
                if(collisionBox.contains(yPoint)) didCollide.y=false;
            }else{
                if(collisionBox.contains(xPoint)) didCollide.x=true;
                if(collisionBox.contains(yPoint)) didCollide.y=true;
            }
        }
        if(didCollide.isFalse())return null;
        return new Collision(didCollide,modifier);
    }

    @Override
    public Collision doCollide(Collider c, Vec2 offset) {
        return switch (c) {
            case BoxCollider bc -> boxColliderHandler(bc, offset);
            case TilemapCollider tc -> tilemapCollisionHandler(tc, offset);
            default -> {
                System.out.println("Collider type not handled by PointCollider");
                yield null;
            }
        };
    }

    @Override
    public Collision getReverseCollision(Collision collision) {
        return new Collision(collision.collisions,modifier);
    }

    @Override
    public void onCollide(ColliderType colliderType, Collision collision) {
        switch (colliderType){
            case SOLID_THICK_INERT:
                if(parent==null)break;
                parent.destroyProjectile();
                break;
            default:
                break;
        }

        switch (vfxType){
            case VFX_PIERCING_METAL:
                if(colliderType==ColliderType.NONE_TRIGGER || colliderType==ColliderType.NONE_DAMAGE_DEALER)break;
                new Vfx(offset, Config.smallTileSize, DataGen.getPiercingTextures(),5);
                break;
            default:
                break;
        }
    }

    @Override
    public Vec2 getOffset() {
        return offset;
    }

    @Override
    public boolean isInverted() {
        return inverted;
    }

    @Override
    public DynamicSprite getParent() {
        return null;
    }

    @Override
    public double getFriction() {
        return friction;
    }

    @Override
    public void setOffset() {
        if(parent==null)return;
        this.offset=Vec2.add(initialOffset,parent.getCurrentPosition());
    }

    @Override
    public ColliderType getColliderType() {
        return colliderType;
    }
}
