package main.physics.colliders;

import main.physics.ColliderType;
import main.physics.Collision;
import main.physics.CollisionLayer;
import main.rendering.vfx.Vfx;
import main.rendering.vfx.VfxType;
import main.utils.data.DataGen;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;

public abstract class Collider {
    public final boolean inverted;
    public final double friction;
    public final double modifier;

    public final ColliderType colliderType;
    public final CollisionLayer collisionLayer;

    protected final Vec2 initialOffset;
    protected Vec2 offset;

    protected final VfxType vfxType;
    protected final int vfxCooldown;
    protected final int vfxDuration;
    protected int remainingVfxDelay = 0;

    protected BVec2 blockedAlongAxis = new BVec2();
    protected double encounteredInverseFriction = 1;

    public Collider(
            boolean inverted,
            double friction,
            double modifier,
            ColliderType colliderType,
            CollisionLayer collisionLayer,
            Vec2 initialOffset
    ) {
        this.inverted = inverted;
        this.friction = friction;
        this.modifier = modifier;

        this.colliderType = colliderType;
        this.collisionLayer = collisionLayer;

        this.initialOffset = initialOffset;

        this.vfxType = VfxType.VFX_NONE;
        this.vfxCooldown = 0;
        this.vfxDuration = 1;
    }

    public Collider(
            boolean inverted,
            double friction,
            double modifier,
            ColliderType colliderType,
            CollisionLayer collisionLayer,
            Vec2 initialOffset,
            VfxType vfxType,
            int vfxCooldown,
            int vfxDuration
    ) {
        this.inverted = inverted;
        this.friction = friction;
        this.modifier = modifier;

        this.colliderType = colliderType;
        this.collisionLayer = collisionLayer;

        this.initialOffset = initialOffset;

        this.vfxType = vfxType;
        this.vfxCooldown = vfxCooldown;
        this.vfxDuration = vfxDuration;
    }

    public void resetCollisions() {
        blockedAlongAxis = new BVec2();
        encounteredInverseFriction = 1;
    }

    public void onCollide(Collision collision) {
        updateVfx(collision);
        updatePhysics(collision);
    }

    protected void updateVfx(Collision collision) {
        if(remainingVfxDelay<=0) {
            remainingVfxDelay = vfxCooldown;
            switch (vfxType){
                case VFX_PIERCING_METAL -> new Vfx(
                        offset,
                        DataGen.getPiercingMetalVfxTextures(),
                        vfxDuration
                );
                case VFX_ELECTRICITY -> new Vfx(
                        offset,
                        DataGen.getElectricVfxTextures(),
                        vfxDuration
                );
                case VFX_EXPLOSION -> new Vfx(
                        offset,
                        DataGen.getExplosionVfxTextures(),
                        vfxDuration
                );
            }
        }
    }

    protected void updatePhysics(Collision collision){
        if(collision.didCollide().x) {
            blockedAlongAxis.x = true;
            encounteredInverseFriction = Math.min(encounteredInverseFriction, collision.colliderTargetFriction());
        }
        if(collision.didCollide().y) {
            blockedAlongAxis.y = true;
            encounteredInverseFriction = Math.min(encounteredInverseFriction, collision.colliderTargetFriction());
        }
    }

    public abstract Collision doCollide(Collider c, Vec2 relativeVelocity);

    public abstract void updateOffset();

    public abstract Vec2 getOffset();
    public abstract Vec2 getVelocity();
}
