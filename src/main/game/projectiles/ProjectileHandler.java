package main.game.projectiles;

import main.utils.vectors.Vec2;

public interface ProjectileHandler {
    void fireInDirection(Vec2 initialPosition, double rotation);
    void updateLifetimes();
    void removeProjectile(int id);
}
