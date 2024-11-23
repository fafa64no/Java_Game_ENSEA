package main.game.projectiles;

import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

public interface ProjectileHandler {
    void fireInDirection(Vec2 initialPosition, Vec2 targetPosition);
}
