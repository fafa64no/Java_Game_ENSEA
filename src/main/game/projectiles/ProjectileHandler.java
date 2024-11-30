package main.game.projectiles;

import main.physics.CollisionLayer;
import main.utils.containers.SizedTextureArray;
import main.utils.vectors.Vec2;

import java.util.List;

public interface ProjectileHandler {
    void fireInDirection(Vec2 initialPosition, double rotation, CollisionLayer collisionLayer);
    void fireInDirection(
            Vec2 initialPosition,
            double rotation,
            double projectileSpeed,
            double modifier,
            CollisionLayer collisionLayer
    );
    void removeProjectile(int id);
    List<Projectile> getProjectiles();
    SizedTextureArray getTextures();
}
