package main.physics.colliders;

import main.game.level.target.effects.Effect;
import main.physics.ColliderType;
import main.physics.Collision;
import main.physics.layers.CollisionLayer;
import main.physics.PhysicEngine;
import main.physics.dynamic_objects.DynamicPoint;
import main.rendering.layers.RenderingLayer;
import main.rendering.vfx.Vfx;
import main.rendering.vfx.VfxType;
import main.utils.data.CollisionConfig;
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

    protected DynamicPoint parent;

    public Collider(
            boolean inverted,
            double friction,
            double modifier,
            ColliderType colliderType,
            CollisionLayer collisionLayer,
            Vec2 initialOffset,
            DynamicPoint parent
    ) {
        this.inverted = inverted;
        this.friction = friction;
        this.modifier = modifier;

        this.colliderType = colliderType;
        this.collisionLayer = collisionLayer;

        this.initialOffset = initialOffset;
        this.offset = initialOffset.copy();

        this.vfxType = VfxType.VFX_NONE;
        this.vfxCooldown = 0;
        this.vfxDuration = 1;

        this.parent = parent;

        PhysicEngine.addCollider(this, collisionLayer);
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
            int vfxDuration,
            DynamicPoint parent
    ) {
        this.inverted = inverted;
        this.friction = friction;
        this.modifier = modifier;

        this.colliderType = colliderType;
        this.collisionLayer = collisionLayer;

        this.initialOffset = initialOffset;
        this.offset = initialOffset.copy();

        this.vfxType = vfxType;
        this.vfxCooldown = vfxCooldown;
        this.vfxDuration = vfxDuration;

        this.parent = parent;

        PhysicEngine.addCollider(this, collisionLayer);
    }

    public void addColliderToColliderList() {
        PhysicEngine.addCollider(this,collisionLayer);
    }

    public void removeColliderFromColliderList() {
        PhysicEngine.removeCollider(this);
    }

    public void updateCollider() {
        if(remainingVfxDelay>0)remainingVfxDelay--;
        resetCollisions();
        updateOffset();
    }

    protected void resetCollisions() {
        blockedAlongAxis = new BVec2();
        encounteredInverseFriction = 1;
    }

    public void onCollide(Collision collision) {
        if(CollisionConfig.doesCollisionApplyVfx(collision)) {
            updateVfx(collision);
        }
        if(CollisionConfig.doesCollisionApplyPhysics(collision)) {
            updatePhysics(collision);
        }
        if(CollisionConfig.doesCollisionApplyEffects(collision)) {
            updateEffects(collision);
        }
    }

    protected void updateVfx(Collision collision) {
        if(remainingVfxDelay<=0) {
            remainingVfxDelay = vfxCooldown;
            new Vfx(offset, vfxType, vfxDuration, RenderingLayer.RENDERING_LAYER_FLYING_TOP);
        }
    }

    protected void updatePhysics(Collision collision) {
        if(collision.didCollide().x) {
            blockedAlongAxis.x = true;
            encounteredInverseFriction = Math.min(encounteredInverseFriction, collision.colliderTargetFriction());
        }
        if(collision.didCollide().y) {
            blockedAlongAxis.y = true;
            encounteredInverseFriction = Math.min(encounteredInverseFriction, collision.colliderTargetFriction());
        }
    }

    protected void updateEffects(Collision collision) {
        if(parent != null) {
            switch (collision.colliderTypeTarget()){
                case SOLID_DAMAGE_DEALER,
                     SOLID_THICK_DAMAGE_DEALER,
                     AERIAL_DAMAGE_DEALER,
                     AERIAL_THICK_DAMAGE_DEALER,
                     POINT_DAMAGE_DEALER
                        -> parent.applyEffect(Effect.DAMAGE, collision.colliderTargetModifier());
            }
        }
    }

    protected void updateOffset() {
        if(parent != null) {
            offset = Vec2.add(initialOffset, parent.getPosition());
        }
    }

    public Vec2 getOffset() {
        return offset;
    }

    public Vec2 getVelocity() {
        if(parent != null) {
            return parent.getCurrentVelocity();
        } else {
            return new Vec2();
        }
    }

    public BVec2 isBlockedAlongAxis() {
        return blockedAlongAxis;
    }

    public double getEncounteredInverseFriction() {
        return encounteredInverseFriction;
    }

    public Collision doCollide(Collider c, Vec2 relativeVelocity) {
        return switch (c) {
            case BoxCollider bc -> boxColliderHandler(bc, relativeVelocity);
            case TileMapCollider tc -> tileMapColliderHandler(tc, relativeVelocity);
            case PointCollider pc -> pointColliderHandler(pc, relativeVelocity);
            default -> {
                System.out.println("Collider type not handled.");
                yield null;
            }
        };
    }

    public void setParent(DynamicPoint dynamicPoint) {
        if(parent == null) {
            parent = dynamicPoint;
        } else {
            System.out.println("Trying to assign a parent to collider which already has one.");
        }
    }

    protected abstract Collision boxColliderHandler(BoxCollider bc, Vec2 relativeVelocity);
    protected abstract Collision tileMapColliderHandler(TileMapCollider tc, Vec2 relativeVelocity);
    protected abstract Collision pointColliderHandler(PointCollider pc, Vec2 relativeVelocity);
}
