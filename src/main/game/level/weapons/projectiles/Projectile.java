package main.game.level.weapons.projectiles;

import main.game.GameEngine;
import main.physics.dynamic_objects.NoControlDynamicPoint;
import main.rendering.sprites.Sprite;
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
    protected final Sprite sprite;

    private final int idInProjectileHandler;

    public Projectile(
            Vec2 initialPosition,
            double initialVelocity,
            double initialRotation,
            int animationFrames,
            int initialLifeSpan,
            int animationSpeed,
            ProjectileHandler parent,
            Sprite sprite,
            int idInProjectileHandler
    ) {
        super(
                initialPosition,
                new Vec2(0,initialVelocity).rotateBy(initialRotation),
                initialRotation
        );
        this.animationFrames = animationFrames;
        this.initialLifeSpan = initialLifeSpan;
        this.remainingMilliseconds = initialLifeSpan;
        this.animationSpeed = animationSpeed;
        this.parent = parent;
        this.sprite = sprite;
        this.idInProjectileHandler = idInProjectileHandler;

        this.sprite.setParent(this);

        addToPhysicsEngine();
        GameEngine.addRequiresUpdates(this);
        sprite.addToRenderList();
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
        GameEngine.removeRequiresUpdates(this);
        sprite.removeFromRenderList();
        if (parent!=null) parent.removeProjectile(idInProjectileHandler);
    }

    public int getCurrentAnimationFrame() {
        return currentAnimationFrame;
    }
}
