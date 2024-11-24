package main.game.projectiles;

import main.utils.vectors.Vec2;

public class Projectile {
    public int remainingMilliseconds;
    public Vec2 currentPosition;
    public Vec2 velocity;
    public double rotation;

    public Projectile(int remainingMilliseconds, Vec2 currentPosition, Vec2 velocity, double rotation) {
        this.remainingMilliseconds = remainingMilliseconds;
        this.currentPosition = currentPosition.copy();
        this.velocity = velocity.copy();
        this.rotation = rotation;
    }
}
