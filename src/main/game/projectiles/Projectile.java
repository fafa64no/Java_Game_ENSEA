package main.game.projectiles;

import main.physics.colliders.RaycastCollider;
import main.utils.data.Config;
import main.utils.vectors.Vec2;

public class Projectile {
    private int remainingMilliseconds;
    private int invisibilityFrames;
    private Vec2 currentPosition;
    private final Vec2 velocity;
    public final double rotation;
    public final RaycastCollider collider;

    public Projectile(int remainingMilliseconds, int invisibilityFrames ,Vec2 currentPosition, double velocity, double rotation) {
        this.remainingMilliseconds = remainingMilliseconds;
        this.invisibilityFrames=invisibilityFrames;
        this.currentPosition = currentPosition.copy();
        this.velocity = Vec2.multiply(new Vec2(0,1).rotateBy(rotation),velocity);
        this.rotation = rotation;
        this.collider = null;
    }

    public void incrementPosition(){
        currentPosition=Vec2.add(currentPosition,velocity);
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
    }
}
