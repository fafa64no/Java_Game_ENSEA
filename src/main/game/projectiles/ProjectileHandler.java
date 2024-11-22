package main.game.projectiles;

import main.utils.vectors.IVec2;

public interface ProjectileHandler {
    void fireInDirection(IVec2 initialPosition, IVec2 targetPosition);
}
