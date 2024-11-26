package main.game.projectiles;

import main.physics.ColliderType;
import main.physics.CollisionLayers;
import main.physics.PhysicEngine;
import main.physics.colliders.Collider;
import main.physics.colliders.PointCollider;
import main.utils.data.Config;
import main.utils.vectors.Vec2;

public class Projectile {
    private int remainingMilliseconds;
    private int invisibilityFrames;
    private Vec2 currentPosition;
    private final Vec2 velocity;
    public final double rotation;
    public final Collider collider;
    public final ProjectileHandler parent;

    private final int idInProjectileHandler;

    public Projectile(int remainingMilliseconds, int invisibilityFrames ,Vec2 currentPosition, double velocity, double rotation, int idInProjectileHandler, ProjectileHandler parent) {
        this.remainingMilliseconds = remainingMilliseconds;
        this.invisibilityFrames=invisibilityFrames;
        this.currentPosition = currentPosition.copy();
        this.velocity = Vec2.multiply(new Vec2(0,1).rotateBy(rotation),velocity);
        this.rotation = rotation;
        this.collider = new PointCollider(
                new Vec2(),
                ColliderType.NONE_DAMAGE_DEALER,
                this
        );
        this.collider.setOffset();
        this.idInProjectileHandler=idInProjectileHandler;
        this.parent=parent;
        PhysicEngine.addCollider(this.collider, CollisionLayers.COLLISION_LAYER_ALLY_PROJECTILES);
    }

    public void incrementPosition(){
        currentPosition=Vec2.add(currentPosition,velocity);
        collider.setOffset();
    }

    public Vec2 getCurrentPosition() {
        return currentPosition;
    }

    public int getRemainingMilliseconds() {
        return remainingMilliseconds;
    }

    public int getInvisibilityFrames() {
        return invisibilityFrames;
    }

    public void decrementTimeRemaining(){
        remainingMilliseconds -= Config.delayBetweenFrames;
        invisibilityFrames--;
        if(remainingMilliseconds<=0)destroyProjectile();;
    }

    public void destroyProjectile(){
        PhysicEngine.removeCollider(collider);
        if (parent!=null) parent.removeProjectile(idInProjectileHandler);
    }
}
