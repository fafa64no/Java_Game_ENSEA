package main.game.level.weapons.projectiles;

import main.physics.layers.CollisionLayer;
import main.rendering.layers.RenderingLayer;
import main.utils.containers.SizedTextureArray;
import main.utils.vectors.Vec2;

public interface ProjectileHandler {
    void fireInDirection(Vec2 initialPosition, double rotation, CollisionLayer collisionLayer, RenderingLayer renderingLayer);
    void fireInDirection(
            Vec2 initialPosition,
            double rotation,
            double projectileSpeed,
            double modifier,
            CollisionLayer collisionLayer,
            RenderingLayer renderingLayer
    );
    void removeProjectile(int id);
    Projectile[] getProjectiles();
    SizedTextureArray getTextures();
}
