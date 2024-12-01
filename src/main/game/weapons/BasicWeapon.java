package main.game.weapons;

import main.game.GameEngine;
import main.game.projectiles.ProjectileHandler;
import main.physics.layers.CollisionLayer;
import main.physics.dynamic_objects.DynamicPoint;
import main.utils.RequiresUpdates;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.Vec2;

public class BasicWeapon implements RequiresUpdates {
    protected final ProjectileHandler projectileHandler;

    protected final int reloadTime;
    protected int remainingReloadTime = 0;
    protected final double spread;

    protected final DynamicPoint parent;
    protected final CollisionLayer collisionLayer;

    protected final Vec2 initialOffset;
    protected final double initialRotation;

    public BasicWeapon(
            ProjectileHandler projectileHandler,
            int reloadTime,
            double spread,
            DynamicPoint parent,
            CollisionLayer collisionLayer,
            Vec2 initialOffset,
            double initialRotation
    ) {
        this.projectileHandler = projectileHandler;
        this.reloadTime = reloadTime;
        this.spread = spread;
        this.parent = parent;
        this.collisionLayer = collisionLayer;
        this.initialOffset = initialOffset;
        this.initialRotation = initialRotation;

        GameEngine.addRequiresUpdates(this);
    }

    public boolean tryToFire() {
        if (remainingReloadTime > 0) {
            return false;
        } else {
            doFire();
            return true;
        }
    }

    protected void doFire() {
        Vec2 firingPosition = Vec2.add(
            initialOffset.copy().rotateBy(parent.getRotation()),
            parent.getPosition()
        );

        double firingRotation = parent.getRotation()
                + initialRotation
                + spread * PseudoRandom.getRandomSpread();

        projectileHandler.fireInDirection(
            firingPosition,
            firingRotation,
            collisionLayer
        );
    }

    @Override
    public void doUpdate() {
        if (remainingReloadTime > 0) remainingReloadTime--;
    }
}
