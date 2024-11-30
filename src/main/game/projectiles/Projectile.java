package main.game.projectiles;

import main.physics.colliders.Collider;
import main.physics.dynamic_objects.NoControlDynamicPoint;
import main.utils.RequiresUpdates;
import main.utils.data.Config;
import main.utils.vectors.Vec2;

public class Projectile extends NoControlDynamicPoint implements RequiresUpdates {
    private int remainingMilliseconds;
    private final int animationFrames;
    private int currentAnimationFrame;

    private final int initialLifeSpan;
    private final int animationSpeed;

    public final ProjectileHandler parent;

    private final int idInProjectileHandler;

    public Projectile(
            Collider mainCollider,
            Vec2 initialPosition,
            double initialVelocity,
            double initialRotation,
            int animationFrames,
            int initialLifeSpan,
            int animationSpeed,
            ProjectileHandler parent,
            int idInProjectileHandler
    ) {
        super(
                mainCollider,
                0,
                0,
                initialPosition,
                new Vec2(0,initialVelocity).rotateBy(initialRotation),
                initialRotation
        );
        this.animationFrames = animationFrames;
        this.initialLifeSpan = initialLifeSpan;
        this.animationSpeed = animationSpeed;
        this.parent = parent;
        this.idInProjectileHandler = idInProjectileHandler;

        addToPhysicsEngine();
    }

    @Override
    public void doUpdate() {
        remainingMilliseconds -= Config.delayBetweenFrames;
        if(remainingMilliseconds <= 0) destroyProjectile();;
        if(animationSpeed > 0){
            currentAnimationFrame = (
                (animationFrames - 1)
                    * animationSpeed
                    * (initialLifeSpan - remainingMilliseconds)
                    / initialLifeSpan
            ) % animationFrames;
        }
    }

    public void destroyProjectile(){
        removeFromRemovePhysicsEngine();
        if (parent!=null) parent.removeProjectile(idInProjectileHandler);
    }

    public int getCurrentAnimationFrame() {
        return currentAnimationFrame;
    }
}
