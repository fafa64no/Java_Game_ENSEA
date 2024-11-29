package main.game.projectiles;

import main.physics.ColliderType;
import main.physics.CollisionLayers;
import main.physics.PhysicEngine;
import main.physics.colliders.Collider;
import main.physics.colliders.PointCollider;
import main.rendering.vfx.VfxType;
import main.utils.data.Config;
import main.utils.vectors.Vec2;

public class Projectile {
    private int remainingMilliseconds;
    private Vec2 currentPosition;
    private final Vec2 velocity;
    private final int animationFrames;
    private int currentAnimationFrame;
    private final int initialLifeSpan;
    private final int animationSpeed;

    public final double rotation;
    public final Collider collider;
    public final ProjectileHandler parent;

    private final int idInProjectileHandler;

    public Projectile(int remainingMilliseconds, int invisibilityFrames ,Vec2 currentPosition, double velocity, double rotation, int idInProjectileHandler, ProjectileHandler parent, CollisionLayers collisionLayer, double modifier, int animationFrames, int animationSpeed, VfxType vfxType, ColliderType colliderType) {
        this.remainingMilliseconds = remainingMilliseconds;
        this.initialLifeSpan = remainingMilliseconds;
        this.velocity = Vec2.multiply(new Vec2(0,1).rotateBy(rotation),velocity);
        this.currentPosition = Vec2.add(currentPosition,Vec2.multiply(this.velocity,invisibilityFrames));
        this.rotation = rotation;
        this.collider = new PointCollider(
                new Vec2(),
                colliderType,
                this,
                modifier,
                vfxType
        );
        this.collider.setOffset();
        this.idInProjectileHandler = idInProjectileHandler;
        this.parent = parent;
        this.animationFrames = animationFrames;
        this.currentAnimationFrame = 0;
        this.animationSpeed = animationSpeed;
        PhysicEngine.addCollider(this.collider, collisionLayer);
    }

    public void incrementPosition(){
        currentPosition=Vec2.add(currentPosition,velocity);
        collider.setOffset();
    }

    public Vec2 getCurrentPosition() {
        return currentPosition;
    }

    public void decrementTimeRemaining(){
        remainingMilliseconds -= Config.delayBetweenFrames;
        if(remainingMilliseconds<=0)destroyProjectile();;
        if(animationSpeed>0){
            currentAnimationFrame=((animationFrames-1)*animationSpeed*(initialLifeSpan-remainingMilliseconds)/initialLifeSpan)%animationFrames;
        }
    }

    public void destroyProjectile(){
        PhysicEngine.removeCollider(collider);
        if (parent!=null) parent.removeProjectile(idInProjectileHandler);
    }

    public int getCurrentAnimationFrame() {
        return currentAnimationFrame;
    }
}
